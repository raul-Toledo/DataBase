package com.example.sir_c.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtCurp, edtNom, edtApat, edtAmat;
    Button btnIns, btnDel, btnUpd, btnQue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtCurp = (EditText)findViewById(R.id.edtCurp);
        edtNom = (EditText)findViewById(R.id.edtNombre);
        edtApat = (EditText)findViewById(R.id.edtPaterno);
        edtAmat = (EditText)findViewById(R.id.edtMaterno);

        btnIns = (Button)findViewById(R.id.btnAlta);
        btnDel = (Button)findViewById(R.id.btnBaja);
        btnUpd = (Button)findViewById(R.id.btnUpdate);
        btnQue = (Button)findViewById(R.id.btnConsulta);

        btnIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbAlta();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbBaja();
            }
        });

        btnQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbConsulta();
            }
        });

        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbModificar();
            }
        });

    }

    private void dbAlta(){
        //Variables para agregar a la DB
        String strCurp, strNom, strApat, strAmat;

        strCurp = edtCurp.getText().toString();
        strNom = edtNom.getText().toString();
        strApat = edtApat.getText().toString();
        strAmat = edtAmat.getText().toString();

        dbClase dbAlumno = new dbClase(getApplicationContext(),
                "alumnos", null, 1);
        SQLiteDatabase sqlDB = dbAlumno.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("idCurp", strCurp);
        registro.put("strNombre", strNom);
        registro.put("strApat", strApat);
        registro.put("strAmat", strAmat);
        // los inserto en la base de datos
        sqlDB.insert("tbAalumno", null, registro);
        sqlDB.close();
        // ponemos los campos a vacío para insertar el siguiente usuario
        edtCurp.setText("");
        edtNom.setText("");
        edtApat.setText("");
        edtAmat.setText("");
        Toast.makeText(getApplicationContext(),
                "Datos del Alumno cargados", Toast.LENGTH_SHORT).show();
    }

    private void dbBaja(){
        String strCurp=edtCurp.getText().toString();
        // aquí borro la base de datos del usuario por el dni
        dbClase dbAlumno = new dbClase(getApplicationContext(),
                "alumnos", null, 1);
        SQLiteDatabase sqlDB = dbAlumno.getWritableDatabase();

        int cant = sqlDB.delete("tbAalumno",
                "idCurp='" + strCurp+"'", null);
        sqlDB.close();
        edtCurp.setText("");
        edtNom.setText("");
        edtApat.setText("");
        edtAmat.setText("");
        if (cant > 0)
            Toast.makeText(getApplicationContext(),
                    "Alumno eliminado", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,
                    "No existe Alumno", Toast.LENGTH_SHORT).show();
    };

    private void dbConsulta(){
        //Variable que va a almacenar la curp que vamos a buscar
        String strCurp=edtCurp.getText().toString();

        dbClase dbAlumno = new dbClase(getApplicationContext(), "alumnos", null, 1);
        SQLiteDatabase sqlDB = dbAlumno.getReadableDatabase();
        Cursor registro = sqlDB.rawQuery("select * from tbAalumno " +
                "where idCurp like '%" + strCurp + "%'", null);

        if (registro.moveToFirst()) {
            edtCurp.setText(registro.getString(0));
            edtNom.setText(registro.getString(1));
            edtApat.setText(registro.getString(2));
            edtAmat.setText(registro.getString(3));
        } else
            Toast.makeText(getApplicationContext(),
                    "No existe ningún usuario con ese CURP", Toast.LENGTH_SHORT).show();
        sqlDB.close();
    };

    private void dbModificar(){
        String strCurp, strNom, strApat, strAmat;
        strCurp = edtCurp.getText().toString();
        strNom = edtNom.getText().toString();
        strApat = edtApat.getText().toString();
        strAmat = edtAmat.getText().toString();

        dbClase dbAlumno = new dbClase(getApplicationContext(), "alumnos", null, 1);
        SQLiteDatabase sqlDB = dbAlumno.getWritableDatabase();

        ContentValues registro = new ContentValues();
        // actualizamos con los nuevos datos, la información cambiada
        registro.put("idCurp", strCurp);
        registro.put("strNombre", strNom);
        registro.put("strApat", strApat);
        registro.put("strAmat", strAmat);
        int cant = sqlDB.update("tbAalumno", registro,
                "idCurp='" + strCurp +"'", null);
        sqlDB.close();
        if (cant > 0)
            Toast.makeText(getApplicationContext(), "Datos modificados con éxito", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "No existe usuario", Toast.LENGTH_SHORT).show();
    };
}
