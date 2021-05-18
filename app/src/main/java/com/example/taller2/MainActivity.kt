package com.example.taller2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taller2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener {
    lateinit var txtpais: TextView
    lateinit var atras : Button
    lateinit var despues : Button
    var busqueda=""
    val countrys = arrayOf("us", "ae", "ar", "at", "au", "be", "bg","br","ca","ch","cn","co","cu" ,"cz" ,"de" ,"eg","fr","gb","gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my","ng","nl","no","nz","ph","pl","pt","ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua","ve","za")
    var cont =0




    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ArticleAdapter
    private val articlesList= mutableListOf<Articles>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchNews.setOnQueryTextListener(this)
        initRecyclerView()
        txtpais=findViewById(R.id.countrytxt)
        txtpais.text="us"
        atras= findViewById(R.id.atras)
        despues= findViewById(R.id.adelante)


        atras.setOnClickListener{
            cont--
            txtpais.text=countrys.get(abs(cont)  % countrys.size)
            searchNew(busqueda)
        }

        despues.setOnClickListener{
            cont++
            txtpais.text=countrys.get(cont % countrys.size)
            searchNew(busqueda)
        }






    }

    private fun initRecyclerView()
    {
        adapter = ArticleAdapter(articlesList)
        binding.rvNews.layoutManager=LinearLayoutManager(this)
        binding.rvNews.adapter= adapter
    }

    private fun searchNew(category: String){
        val api=Retrofit2()

        CoroutineScope(Dispatchers.IO).launch {
            busqueda=category
            val call = api.getService()?.getNewsBYCategorys(txtpais.text.toString(),category,"3a3db84c2e6e4de5a80f92f28aca9d8d")
            val news : NewsResponse?=call?.body()

            runOnUiThread{
                if (call!!.isSuccessful)
                {
                    if (news?.status.equals("ok")) {
                        val articles = news?.articles ?: emptyList()
                        articlesList.clear()
                        articlesList.addAll(articles)
                        adapter.notifyDataSetChanged()
                    }
                    else
                    {
                        showMessage("Error en webservice")
                    }

                }
                else
                {
                    showMessage("Error en retrofit")
                }
                hideKeyBoard()

            }
        }

    }

    private fun hideKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }


    private fun showMessage(message:String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        showMessage(query.toString())
        if (!query.isNullOrEmpty())
        {
            searchNew(query.toLowerCase(Locale.ROOT))
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }


}