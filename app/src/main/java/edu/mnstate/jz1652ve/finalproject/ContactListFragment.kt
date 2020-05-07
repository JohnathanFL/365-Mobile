package edu.mnstate.jz1652ve.finalproject

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.concurrent.thread

class ContactListFragment : Fragment(), ContactDisplay {

    val TAG: String = "ContactListFragment"

    lateinit var me: View
    lateinit var recycler: RecyclerView


    lateinit var adapter: ContactAdapter
    lateinit var dao: ContactHelper

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

        this.adapter = ContactAdapter(this.context as Context, dao.getAll())

        this.recycler.adapter = this.adapter

        return me
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ContactListFragment()
    }

    override fun notifyChanged() {
        this.adapter.contacts = dao.getAll()
        this.adapter.notifyDataSetChanged()
    }
}

class ContactAdapter(var context: Context, var contacts: List<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val honorific: TextView = itemView.findViewById(R.id.honorific)
        val firstName: TextView = itemView.findViewById(R.id.firstName)
        val lastName: TextView = itemView.findViewById(R.id.lastName)
        val rel: TextView = itemView.findViewById(R.id.relation)
        val birthday: TextView = itemView.findViewById(R.id.birthday)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_holder, parent, false))
    }

    override fun getItemCount(): Int = contacts.size

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        Log.d("ContactListFragment", "onBindViewHolder: BOUND")
        val contact = contacts[position]
        holder.honorific.text = when(contact.sex) {
            'M' -> "Mr."
            'F' -> if (contact.married) { "Mrs." } else { "Ms." }
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
    }

}