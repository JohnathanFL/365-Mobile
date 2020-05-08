package edu.mnstate.jz1652ve.finalproject

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.contact_holder.view.*
import java.util.*
import kotlin.concurrent.thread

class ContactListFragment : Fragment() {

    val TAG: String = "ContactListFragment"

    lateinit var me: View
    lateinit var recycler: RecyclerView


    lateinit var adapter: ContactAdapter
    lateinit var dao: ContactHelper

    lateinit var goToMap: (LatLng) -> Unit
    private var getData: () -> List<Contact> = { dao.getAll() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.dao = ContactHelper(this.context!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.me = inflater.inflate(R.layout.fragment_contact_list, container, false)
        this.recycler = this.me.findViewById(R.id.recyclerView)
        this.recycler.layoutManager = LinearLayoutManager(this.context)

        this.adapter = ContactAdapter(this, dao.getAll())

        this.recycler.adapter = this.adapter

        setHasOptionsMenu(true)


        return me
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ContactListFragment()
    }

    fun notifyChanged() {
        this.adapter.contacts = getData()
        this.adapter.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contact_list_menu, menu)

        val searchItem = menu.findItem(R.id.optSearch)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                getData = { dao.getLike(newText!!) }
//                activity?.actionBar?.title = getString(R.string.searching) + newText
                notifyChanged()
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: Change sort")
        when(item.itemId) {
            R.id.optSortByFName -> {
                getData = { dao.getSortFName() }
//                activity!!.actionBar!!.setTitle(R.string.sort_by_first_name)
                notifyChanged()
            }
            R.id.optSortByLName -> {
                getData = { dao.getSortLName() }
//                activity!!.actionBar!!.setTitle(R.string.sort_by_last_name)
                notifyChanged()
            }
            R.id.optParent -> {
                getData = { dao.getByRel(RelType.Parent) }
//                activity!!.actionBar!!.setTitle(R.string.parent)
                notifyChanged()
            }
            R.id.optChild -> {
                getData = { dao.getByRel(RelType.Child) }
//                activity!!.actionBar!!.setTitle(R.string.child)
                notifyChanged()
            }
            R.id.optSibling -> {
                getData = { dao.getByRel(RelType.Sibling) }
//                activity!!.actionBar!!.setTitle(R.string.sibling)
                notifyChanged()
            }
            R.id.optExtFam -> {
                getData = { dao.getByRel(RelType.ExtFam) }
//                activity!!.actionBar!!.setTitle(R.string.extFam)
                notifyChanged()
            }
            R.id.optAcquaintance -> {
                getData = { dao.getByRel(RelType.Acquaintance) }
//                activity!!.actionBar!!.setTitle(R.string.acquaintance)
                notifyChanged()
            }
            R.id.optFriend -> {
                getData = { dao.getByRel(RelType.Friend) }
//                activity!!.actionBar!!.setTitle(R.string.friend)
                notifyChanged()
            }
            R.id.optCloseFriend -> {    getData = { dao.getByRel(RelType.CloseFriend) }
//                activity!!.actionBar!!.setTitle(R.string.closeFriend)
                notifyChanged()
            }
            R.id.optSigOth -> {
                getData = { dao.getByRel(RelType.SigOth) }
//                activity!!.actionBar!!.setTitle(R.string.sigOth)
                notifyChanged()
            }
        }


        return true
    }
}

class ContactAdapter(val frag: ContactListFragment, var contacts: List<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    val context = frag.context!!

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val honorific: TextView = itemView.findViewById(R.id.honorific)
        val firstName: TextView = itemView.findViewById(R.id.firstName)
        val lastName: TextView = itemView.findViewById(R.id.lastName)
        val rel: TextView = itemView.findViewById(R.id.relation)
        val birthday: TextView = itemView.findViewById(R.id.birthday)
        val delItemBtn: ImageButton = itemView.findViewById(R.id.delItemBtn)
        val goMapBtn: ImageButton = itemView.findViewById(R.id.showMapBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_holder, parent, false))
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.honorific.text = when(contact.sex) {
            'M' -> context.getString(R.string.maleHonorific)
            'F' -> if (contact.married) { context.getString(R.string.femMarried) } else { context.getString(
                            R.string.femUnmarried) }
            else -> TODO()
        }
        holder.firstName.text = contact.firstName
        holder.lastName.text = contact.lastName
        holder.rel.text = when(relFromInt(contact.rel)) {
            RelType.Parent -> context.getString(R.string.parent)
            RelType.Child -> context.getString(R.string.child)
            RelType.Sibling -> context.getString(R.string.sibling)
            RelType.ExtFam -> context.getString(R.string.extFam)
            RelType.Acquaintance -> context.getString(R.string.acquaintance)
            RelType.Friend -> context.getString(R.string.friend)
            RelType.CloseFriend -> context.getString(R.string.closeFriend)
            RelType.SigOth -> context.getString(R.string.sigOth)
        }

        holder.birthday.text = contact.birthday

        holder.delItemBtn.setOnClickListener {
            frag.dao.delete(contact.id!!)
            frag.notifyChanged()
        }

        holder.goMapBtn.setOnClickListener { frag.goToMap(LatLng(contact.lat, contact.lng)) }
    }

}