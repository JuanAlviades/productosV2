package com.example.productosv2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

public class Agregar extends AppCompatActivity  {
    EditText usuario, correo, contrasena; // Cambié los nombres de las variables
    Button btnregistrar;
    Button btninternal1;

    @SuppressLint({"CutPasteId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        // Inicialización de vistas
        usuario = findViewById(R.id.usuario); // Cambié los nombres de las variables
        correo = findViewById(R.id.correo); // Cambié los nombres de las variables
        contrasena = findViewById(R.id.contrasena); // Cambié los nombres de las variables
        btnregistrar = findViewById(R.id.btnregistrar);
        btninternal1 = findViewById(R.id.btninternal1);

        btninternal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Agregar.this, registroProducto.class));
            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarDatos();
            }
        });
    }

    private void registrarDatos() {
        String nombre = usuario.getText().toString().trim(); // Cambié el nombre de la variable
        String correoStr = correo.getText().toString().trim(); // Cambié el nombre de la variable
        String contrasenaStr = contrasena.getText().toString().trim(); // Cambié el nombre de la variable

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando, ya casi");

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre", Toast.LENGTH_SHORT).show();
        } else if (correoStr.isEmpty()) { // Cambié el nombre de la variable
            Toast.makeText(this, "Ingrese el correo", Toast.LENGTH_SHORT).show();
        } else if (contrasenaStr.isEmpty()) { // Cambié el nombre de la variable
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.10.15/crud/insertar.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    if (response.equalsIgnoreCase("datos insertados")) {
                        Toast.makeText(Agregar.this, "Registros ingresados", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Agregar.class));
                        finish();
                    } else {
                        Toast.makeText(Agregar.this, "Registros ingresados", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Agregar.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show(); // Mensaje de error más detallado
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nombre", nombre);
                    params.put("correo", correoStr); // Cambié el nombre de la variable
                    params.put("contrasena", contrasenaStr); // Cambié el nombre de la variable
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Agregar.this);
            requestQueue.add(request);
            usuario.setText(""); // Cambié el nombre de la variable
            correo.setText(""); // Cambié el nombre de la variable
            contrasena.setText(""); // Cambié el nombre de la variable
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
