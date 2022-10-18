package app.date.carol.groceryapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_items")
data class GroceryItems(

    @ColumnInfo(name = "itemName")
    var itemName: String,

    @ColumnInfo(name = "itemQuantity")
    var itemQuantity: Int,

    @ColumnInfo(name = "ItemPrice")
    var itemPrice: Int

) {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
}

