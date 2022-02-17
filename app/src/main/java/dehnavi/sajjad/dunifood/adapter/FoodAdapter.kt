package dehnavi.sajjad.dunifood.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dehnavi.sajjad.dunifood.databinding.ItemFoodBinding
import dehnavi.sajjad.dunifood.model.Food

class FoodAdapter(
    val context: Context,
    val foodList: ArrayList<Food>,
    val foodListener: FoodListener
) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        private val binding = binding;

        @SuppressLint("SetTextI18n")
        fun onBind(position: Int) {
            val food = foodList[position]
            binding.titleFood.text = food.title
            binding.descriptionFood.text = food.description
            binding.ratingBar.rating = food.rate
            binding.numRate.text = food.rate.toString()
            binding.priceFood.text = "$" + food.price.toString()

            Glide.with(context)
                .load(food.imgLink)
                .into(binding.imgFood)

            itemView.setOnClickListener {
                foodListener.onClick(food, position)
            }

            itemView.setOnLongClickListener {
                foodListener.onLongClick(food, position)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun addItem(food: Food, position: Int) {
        foodList.add(position, food)
        notifyItemInserted(position)
    }

    fun removeItem(food: Food, position: Int) {
        foodList.remove(food)
        notifyItemRemoved(position)
    }

    fun updateItem(food: Food, position: Int) {
        foodList.removeAt(position)
        foodList.add(position, food)
        notifyItemChanged(position)
    }

    fun addItems(foodList: ArrayList<Food>) {
        this.foodList.addAll(foodList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        foodList.clear()
        notifyDataSetChanged()
    }

    interface FoodListener {
        fun onClick(food: Food, position: Int)

        fun onLongClick(food: Food, position: Int)
    }
}