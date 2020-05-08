package edu.mnstate.jz1652ve.finalproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AddContactActivity : Activity() {
    lateinit var firstName: EditText
    lateinit var lastName: EditText
    lateinit var genderGroup: RadioGroup
    lateinit var relType: Spinner
    lateinit var yearBar: SeekBar
    lateinit var bday: DatePicker
    lateinit var phone: EditText
    lateinit var married: CheckBox
    lateinit var locPickBtn: ImageButton
    lateinit var latField: EditText
    lateinit var lngField: EditText

    lateinit var addBtn: ImageButton

    var api = Retrofit.Builder()
        .baseUrl("https://api.genderize.io")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GenderizeAPI::class.java)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != 1) return
        latField.setText(data!!.getDoubleExtra("lat", 45.0).toString())
        lngField.setText(data.getDoubleExtra("lng", 45.0).toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        firstName = findViewById(R.id.editFirstName)
        lastName = findViewById(R.id.editLastName)
        genderGroup = findViewById(R.id.relClassGroup)
        relType = findViewById(R.id.relTypeSpinner)
        yearBar = findViewById(R.id.yearSeek)
        bday = findViewById(R.id.bdayPicker)
        addBtn = findViewById(R.id.addBtn)
        phone = findViewById(R.id.editTextPhone)
        married = findViewById(R.id.marriedCheck)


        locPickBtn = findViewById(R.id.locPicker)
        latField = findViewById(R.id.latField)
        lngField = findViewById(R.id.lngField)

        locPickBtn.setOnClickListener {
            val getMeMyLoc = Intent(this, LocPickerActivity::class.java)
            startActivityForResult(getMeMyLoc, 0)
        }

        yearBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var lastState: Int = 11
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!fromUser) return;
                val delta = progress - lastState
                lastState = progress
                bday.updateDate(bday.year + delta, bday.month, bday.dayOfMonth)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                lastState = 11
                yearBar.progress = 11
            }

        })

        // Doing it on focus leave to limit the number of requests made
        firstName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus)
                api.checkName(firstName.text.toString()).enqueue(object :
                    Callback<GenderizeResult> {
                    override fun onFailure(call: Call<GenderizeResult>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(
                        call: Call<GenderizeResult>,
                        response: Response<GenderizeResult>
                    ) {
                        if (response.body() != null) {
                            Log.d("AddContactActivity", "onResponse: Got " + response.body())
                            val which = when (response.body()?.gender) {
                                "male" -> R.id.maleRadio
                                "female" -> R.id.femRadio
                                else -> TODO("Not possible")
                            }
                            genderGroup.check(which)
                        }
                    }

                })
        }

        addBtn.setOnClickListener {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            if (firstName.text.isBlank()) {
                Toast.makeText(this, getString(R.string.mustEnterFirst), Toast.LENGTH_LONG).show()
                firstName.requestFocus()
                imm.showSoftInput(firstName, InputMethodManager.SHOW_IMPLICIT);
                return@setOnClickListener
            } else if (lastName.text.isBlank()) {
                Toast.makeText(this, getString(R.string.mustEnterLast), Toast.LENGTH_LONG).show()
                lastName.requestFocus()
                imm.showSoftInput(lastName, InputMethodManager.SHOW_IMPLICIT);
                return@setOnClickListener
            } else if (phone.text.isBlank()) {
                Toast.makeText(this, getString(R.string.mustEnterPhone), Toast.LENGTH_LONG).show()
                phone.requestFocus()
                imm.showSoftInput(phone, InputMethodManager.SHOW_IMPLICIT);
                return@setOnClickListener
            } else if (!phone.text.matches(Regex("""(\+?\d{1,2})?(\s*|-?)\d{3}(\s*|-?)\d{3}(\s*|-?)\d{4}"""))) {
                Toast.makeText(
                    this,
                    getString(R.string.validPhone),
                    Toast.LENGTH_LONG
                ).show()
                phone.requestFocus()
                imm.showSoftInput(phone, InputMethodManager.SHOW_IMPLICIT);
                return@setOnClickListener
            }


            val sex = when (genderGroup.checkedRadioButtonId) {
                R.id.maleRadio -> 'M'
                R.id.femRadio -> 'F'
                else -> { // Must be unselected
                    Toast.makeText(this, getString(R.string.must_select_gender), Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
            }

            if (latField.text.isBlank() || lngField.text.isBlank()) {
                Toast.makeText(this, getString(R.string.must_select_loc), Toast.LENGTH_LONG).show()
                locPickBtn.performClick()
                return@setOnClickListener
            }

            val lat = latField.text.toString().toDoubleOrNull() ?: 45.0
            val lng = lngField.text.toString().toDoubleOrNull() ?: 45.0

            val newC = Contact(
                firstName.text.toString(),
                lastName.text.toString(),
                sex,
                married.isChecked,
                relType.selectedItemPosition,
                "" + bday.year + "-" + bday.month + "-" + bday.dayOfMonth,
                phone.text.toString(),
                lat,
                lng
            )

            val helper = ContactHelper(this)
            helper.insert(newC)
            finish()
        }
    }
}