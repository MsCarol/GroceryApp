package app.date.carol.groceryapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var itemsRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRV = findViewById(R.id.idRVItems)
        addFAB = findViewById(R.id.idFABAdd)
        list = ArrayList<GroceryItems>()
        groceryRVAdapter = GroceryRVAdapter(list, this)
        itemsRV.layoutManager = LinearLayoutManager(this)
        itemsRV.adapter = groceryRVAdapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this,factory).get(GroceryViewModel::class.java)
        groceryViewModel.getAllGroceryItems().observe(this) {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        }

        addFAB.setOnClickListener {
            openDialog()
        }


    }
    fun openDialog()
    {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)
        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdit = dialog.findViewById<EditText>(R.id.idEditItemName)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.idEditItemPrice)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.idEditItemQuantity)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName : String = itemEdit.text.toString()
            val itemPrice : String = itemPriceEdt.text.toString()
            val itemQuantity : String = itemQuantityEdt.text.toString()
            val qty : Int = itemQuantity.toInt()
            val pr : Int = itemPrice.toInt()
            if(itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()){
                val items = GroceryItems(itemName,qty,pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext, "Item inserted", Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()

            } else {
                Toast.makeText(applicationContext, "Please enter all the data", Toast.LENGTH_SHORT).show()

            }
        }
        dialog.show()

    }

    fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "Item Deleted", Toast.LENGTH_SHORT).show()
    }
}