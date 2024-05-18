package com.example.productosv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registroProducto extends AppCompatActivity {

    EditText nomProducto, precioProducto, descProducto; // Cambié los nombres de las variables
    Button volver;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        // Inicialización de vistas
        nomProducto = findViewById(R.id.nom_producto); // Cambié los nombres de las variables
        precioProducto = findViewById(R.id.precio_producto); // Cambié los nombres de las variables
        descProducto = findViewById(R.id.desc_producto); // Cambié los nombres de las variables
        volver = findViewById(R.id.volver);
        btnRegistrar = findViewById(R.id.btnregistrar);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registroProducto.this, Agregar.class));
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarDatos();
            }
        });
    }

    private void registrarDatos() {
        String nombreProducto = nomProducto.getText().toString().trim(); // Cambié el nombre de la variable
        String precioProductoStr = precioProducto.getText().toString().trim(); // Cambié el nombre de la variable
        String descProductoStr = descProducto.getText().toString().trim(); // Cambié el nombre de la variable

        if (nombreProducto.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del producto", Toast.LENGTH_SHORT).show();
        } else if (precioProductoStr.isEmpty()) {
            Toast.makeText(this, "Ingrese el precio del producto", Toast.LENGTH_SHORT).show();
        } else if (descProductoStr.isEmpty()) {
            Toast.makeText(this, "Ingrese la descripción del producto", Toast.LENGTH_SHORT).show();
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Cargando...");
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.10.15/crud/productos/insertar.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            if (response.equalsIgnoreCase("Datos insertados")) {
                                Toast.makeText(registroProducto.this, "Producto registrado", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Agregar.class));
                                finish();
                            } else {
                                Toast.makeText(registroProducto.this, "Producto registrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(registroProducto.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nombre", nombreProducto);
                    params.put("valor", precioProductoStr); // Cambié el nombre de la variable
                    params.put("descripcion", descProductoStr);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(registroProducto.this);
            requestQueue.add(request);

            nomProducto.setText(""); // Cambié el nombre de la variable
            precioProducto.setText(""); // Cambié el nombre de la variable
            descProducto.setText(""); // Cambié el nombre de la variable
        }
    }
}
