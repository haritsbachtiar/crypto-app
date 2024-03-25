package com.haritsbachtiar.cryptoapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haritsbachtiar.cryptoapp.databinding.ItemCryptoBinding
import com.haritsbachtiar.cryptoapp.domain.CryptoFeedItem

class CryptoAdapter(
    private val items: List<CryptoFeedItem>
) : RecyclerView.Adapter<CryptoAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(
        private val binding: ItemCryptoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun newInstance(parent: ViewGroup): MyViewHolder {
                return MyViewHolder(
                    ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }

        fun bind(item: CryptoFeedItem) {
            with(binding) {
                tvCrypto.text = item.coinInfo.fullName
            }
        }
    }
}