package edu.mnstate.jz1652ve.finalproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        latField.setText("" + data!!.getDoubleExtra("lat", 45.0))
        lngField.setText("" + data!!.getDoubleExtra("lng", 45.0))
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

        // bday.maxDate TODO

        yearBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var lastState: Int = 6
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(!fromUser) return;
                val delta = progress - lastState
                lastState = progress
                bday.updateDate(bday.year + delta, bday.month, bday.dayOfMonth)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                lastState = 6
                yearBar.progress = 6
            }

        })

        addBtn.setOnClickListener {
            val sex = when(genderGroup.checkedRadioButtonId) {
                R.id.maleRadio -> 'M'
                R.id.femRadio -> 'F'
                else -> {
                    Toast.makeText(this, "Must select a gender.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            val lat = latField.text.toString().toDoubleOrNull() ?: 45.0
            val lng = lngField.text.toString().toDoubleOrNull() ?: 45.0

            val newC = Contact(firstName.text.toString(), lastName.text.toString(), sex, married.isChecked, relType.selectedItemPosition, "" + bday.year + "-" + bday.month + "-" + bday.dayOfMonth, phone.text.toString(), lat, lng)

            val helper = ContactHelper(this)
            helper.insert(newC)
            finish()
        }
    }
}