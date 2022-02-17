package dehnavi.sajjad.dunifood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import dehnavi.sajjad.dunifood.adapter.FoodAdapter
import dehnavi.sajjad.dunifood.databinding.ActivityMainBinding
import dehnavi.sajjad.dunifood.databinding.DialogAddBinding
import dehnavi.sajjad.dunifood.databinding.DialogDeleteBinding
import dehnavi.sajjad.dunifood.databinding.DialogEditBinding
import dehnavi.sajjad.dunifood.model.Food

class MainActivity : AppCompatActivity(), FoodAdapter.FoodListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodList: ArrayList<Food>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        foodList = getFoodData()
        setUpFoodList()
        setUpAddItem()
        setUpSearch()
    }

    private fun setUpSearch() {

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                foodAdapter.clearItems()
                if (s.toString().isNotEmpty()) {
                    val filter = foodList.filter {
                        it.title.contains(s.toString())
                    }
                    foodAdapter.addItems(filter as ArrayList<Food>)
                } else
                    foodAdapter.addItems(foodList.clone() as ArrayList<Food>)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun setUpAddItem() {
        binding.imgAdd.setOnClickListener {
            val dialogBinding = DialogAddBinding.inflate(layoutInflater)

            val addDialog = createDialog(dialogBinding.root)

            dialogBinding.btnAddFood.setOnClickListener {
                if (
                    dialogBinding.inputTitle.editText!!.text.toString().isNotEmpty() &&
                    dialogBinding.inputDescription.editText!!.text.toString().isNotEmpty() &&
                    dialogBinding.inputRate.editText!!.text.toString().isNotEmpty() &&
                    dialogBinding.inputPrice.editText!!.text.toString().isNotEmpty()
                ) {

                    val newFood = Food(
                        dialogBinding.inputTitle.editText!!.text.toString(),
                        dialogBinding.inputDescription.editText!!.text.toString(),
                        dialogBinding.inputRate.editText!!.text.toString().toFloat(),
                        dialogBinding.inputPrice.editText!!.text.toString().toFloat(),
                        "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg"
                    )

                    foodAdapter.addItem(newFood, 0)
                    foodList.add(newFood)
                    binding.foodRecyclerView.scrollToPosition(0)
                    addDialog.dismiss()

                } else {
                    Toast.makeText(this, "enter value", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

    private fun createDialog(view: View): AlertDialog {
        val dialog = AlertDialog.Builder(this).create()
        dialog.setView(view)
        dialog.setCancelable(true)
        dialog.show()
        return dialog
    }

    private fun setUpFoodList() {
        foodAdapter = FoodAdapter(this, foodList.clone() as ArrayList<Food>, this)
        binding.foodRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.foodRecyclerView.adapter = foodAdapter
    }

    private fun getFoodData(): ArrayList<Food> {
        return arrayListOf(
            Food(
                "Hamburger",
                "15",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg"
            ),
            Food(
                "Grilled fish",
                "20",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg"
            ),
            Food(
                "Lasania",
                "40",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg"
            ),
            Food(
                "pizza",
                "10",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
            ),
            Food(
                "Sushi",
                "20",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
            ),
            Food(
                "Roasted Fish",
                "40",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
            ),
            Food(
                "Fried chicken",
                "70",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",

                ),
            Food(
                "Vegetable salad",
                "12",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
            ),
            Food(
                "Grilled chicken",
                "10",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",

                ),
            Food(
                "Baryooni",
                "16",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",

                ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                2f,
                12f, "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",

                ),
            Food(
                "Rice",
                "12.5",
                2f,
                12f,
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
            ),
        )
    }

    override fun onClick(food: Food, position: Int) {
        setUpDeleteItem(food, position)
    }

    private fun setUpDeleteItem(food: Food, position: Int) {
        val deleteBinding = DialogDeleteBinding.inflate(layoutInflater)
        val deleteDialog = createDialog(deleteBinding.root)

        deleteBinding.btnCancel.setOnClickListener { deleteDialog.dismiss() }

        deleteBinding.btnDeleteFood.setOnClickListener {
            foodAdapter.removeItem(food, position)
            foodList.remove(food)
            deleteDialog.dismiss()
        }
    }

    override fun onLongClick(food: Food, position: Int) {
        setUpEditFood(food, position)
    }

    private fun setUpEditFood(food: Food, position: Int) {
        val bindingEdit = DialogEditBinding.inflate(layoutInflater)
        val dialogEdit = createDialog(bindingEdit.root)


        bindingEdit.inputDescription.editText!!.setText(food.description)
        bindingEdit.inputTitle.editText!!.setText(food.title)
        bindingEdit.inputPrice.editText!!.setText(food.price.toString())
        bindingEdit.inputRate.editText!!.setText(food.rate.toString())

        bindingEdit.btnEditFood.setOnClickListener {
            if (
                bindingEdit.inputTitle.editText!!.text.toString().isNotEmpty() &&
                bindingEdit.inputDescription.editText!!.text.toString().isNotEmpty() &&
                bindingEdit.inputRate.editText!!.text.toString().isNotEmpty() &&
                bindingEdit.inputPrice.editText!!.text.toString().isNotEmpty()
            ) {
                foodAdapter.updateItem(
                    Food(
                        bindingEdit.inputTitle.editText!!.text.toString(),
                        bindingEdit.inputDescription.editText!!.text.toString(),
                        bindingEdit.inputRate.editText!!.text.toString().toFloat(),
                        bindingEdit.inputPrice.editText!!.text.toString().toFloat(),
                        food.imgLink
                    ), position
                )
                dialogEdit.dismiss()

            } else {
                Toast.makeText(this, "enter value", Toast.LENGTH_SHORT).show()
            }
        }

        bindingEdit.btnCancel.setOnClickListener { dialogEdit.dismiss() }


    }
}