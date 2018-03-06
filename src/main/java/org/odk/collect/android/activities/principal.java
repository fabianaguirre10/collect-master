package org.odk.collect.android.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.odk.collect.android.R;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.database.BaseDatosEngine.BaseDatosEngine;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.Branch;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.BranchSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.Capania;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.CodigoSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.ConfiguracionDB;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.ConfiguracionSession;
import org.odk.collect.android.database.BaseDatosEngine.Entidades.CuentaSession;
import org.odk.collect.android.database.BaseDatosEngine.EstructuraBD;
import org.odk.collect.android.logic.AdapterItem;
import org.odk.collect.android.logic.Category;
import org.odk.collect.android.preferences.AboutPreferencesActivity;
import org.odk.collect.android.preferences.AdminKeys;
import org.odk.collect.android.preferences.AdminPreferencesActivity;
import org.odk.collect.android.preferences.PreferencesActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class principal extends AppCompatActivity {
    private static final int PASSWORD_DIALOG = 1;
    final ConfiguracionSession objconfiguracionSession = new ConfiguracionSession();
    final BranchSession objBranchSeccion = new BranchSession();
    final CodigoSession objcodigoSession = new CodigoSession();
    final CuentaSession objcuentaSession = new CuentaSession();

    Engine_util objutil;
    TextView txtnombrecampania;
    Spinner cmbnumeroruta;

    EditText txtbuscar;
    private SharedPreferences adminPreferences;
    ProgressDialog progress;
    JSONArray respJSON= new JSONArray();
    ArrayList<Category> category = new ArrayList<Category>();
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtnombrecampania = (TextView) findViewById(R.id.txnombrecampania);
        cmbnumeroruta = (Spinner) findViewById(R.id.cmbnumeroruta);
        txtbuscar = (EditText) findViewById(R.id.txtcodbusqueda);

        objutil= new Engine_util();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        lv = (ListView) findViewById(R.id.listView);



        adminPreferences = this.getSharedPreferences(
                AdminPreferencesActivity.ADMIN_PREFERENCES, 0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (objcuentaSession.getCu_ID() != "") {
                    final android.app.AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new android.app.AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new android.app.AlertDialog.Builder(view.getContext());
                    }
                    builder.setTitle("Código de local nuevo");
                    builder.setMessage("¿Desea obtener código nuevo?");
                    builder.setPositiveButton("Asignar Código", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            CargarNuevos();
                            if (objcodigoSession.getcId() != "") {
                                objBranchSeccion.setE_nuevo("nuevo");
                                Intent intent = new Intent(getApplication(), FormChooserList.class);
                                startActivityForResult(intent, 0);
                            } else {
                                ConnectivityManager connMgr = (ConnectivityManager)
                                        getSystemService(getApplication().CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                                if (networkInfo != null && networkInfo.isConnected()) {
                                    // DatosConsulta();

                                    CargarCodigosNuvos fetchJsonTask = new CargarCodigosNuvos(builder.getContext());
                                    fetchJsonTask.execute();
                                    //fetchJsonTask.getStatus();

                                } else {
                                    Toast.makeText(getApplication(),
                                            "Sin Conexión a la red seleccione Digitar Código", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }
                    });
                    builder.setNegativeButton("Digitar Código", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            objBranchSeccion.setE_ID("");
                            objBranchSeccion.setE_idbranch("");
                            objBranchSeccion.setE_idAccount("");
                            objBranchSeccion.setE_externalCode("");
                            objBranchSeccion.setE_code("");
                            objBranchSeccion.setE_neighborhood("");
                            objBranchSeccion.setE_mainStreet("");
                            objBranchSeccion.setE_reference("");
                            objBranchSeccion.setE_propietario("");
                            objBranchSeccion.setE_uriformulario("");
                            objBranchSeccion.setE_idprovince("");
                            objBranchSeccion.setE_iddistrict("");
                            objBranchSeccion.setE_idParish("");
                            objBranchSeccion.setE_rutaaggregate("0");
                            objBranchSeccion.setE_imeI_ID("");
                            objBranchSeccion.setE_nuevo("nuevo");

                            objcodigoSession.setcId("");
                            objcodigoSession.setC_idAccount("");
                            objcodigoSession.setC_code("");
                            objcodigoSession.setC_estado("");
                            objcodigoSession.setC_uri("");
                            objcodigoSession.setC_imei_id("");
                            Intent intent = new Intent(getApplication(), FormChooserList.class);
                            startActivityForResult(intent, 0);
                        }
                    });
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.show();


                } else

                {
                    Toast.makeText(getApplication(),
                            "Seleccione una cuenta", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button btnbuscartareacodigo = (Button) findViewById(R.id.btnbuscartareacodigo);
        if (objconfiguracionSession.getCnf_CampaniaNombre() == null) {
            objutil.CargarConfiguracion();
            if (objconfiguracionSession.getCnf_CampaniaNombre() == null) {
                Toast.makeText(getApplication(),
                        "Configurar Cuenta..!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, configuracion.class);
                startActivity(intent);
            } else {
                txtnombrecampania.setText(objconfiguracionSession.getCnf_CampaniaNombre().toString());
                ListarRutasEngine();
            }
        } else {
            txtnombrecampania.setText(objconfiguracionSession.getCnf_CampaniaNombre().toString());
            ListarRutasEngine();
        }
        cmbnumeroruta.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        String valor = (String) parent.getItemAtPosition(position);

                        buscarlocalesruta(valor, objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        btnbuscartareacodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtbuscar.getText().toString().equals("") == false) {
                    buscarlocalesrutaCodigo(cmbnumeroruta.getSelectedItem().toString(), objconfiguracionSession.getCnf_factorbusqueda().toString().toUpperCase());
                }
            }

        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                if (cmbnumeroruta.getSelectedItem() != null) {
                    Category currentLead = (Category) parent.getItemAtPosition(position);
                   String where = "where 1=1 ";
                    where = where + " and idbranch ='" + currentLead.getCategoryId() + "' ";
                    Cursor objseleccionado= objutil.SeleccionarLocal(where);

                    objBranchSeccion.setE_ID("");
                    objBranchSeccion.setE_idbranch("");
                    objBranchSeccion.setE_idAccount("");
                    objBranchSeccion.setE_externalCode("");
                    objBranchSeccion.setE_code("");
                    objBranchSeccion.setE_neighborhood("");
                    objBranchSeccion.setE_mainStreet("");
                    objBranchSeccion.setE_reference("");
                    objBranchSeccion.setE_propietario("");
                    objBranchSeccion.setE_uriformulario("");
                    objBranchSeccion.setE_idprovince("");
                    objBranchSeccion.setE_iddistrict("");
                    objBranchSeccion.setE_idParish("");
                    objBranchSeccion.setE_rutaaggregate("0");
                    objBranchSeccion.setE_imeI_ID("");
                    objBranchSeccion.setE_nuevo("");
                    objBranchSeccion.setE_name("");
                    objcodigoSession.setcId("");
                    objcodigoSession.setC_idAccount("");
                    objcodigoSession.setC_code("");
                    objcodigoSession.setC_estado("");
                    objcodigoSession.setC_uri("");
                    objcodigoSession.setC_imei_id("");

                    if (objseleccionado.moveToFirst()) {
                        do {
                            objBranchSeccion.setE_ID(objseleccionado.getString(0));
                            objBranchSeccion.setE_idbranch(objseleccionado.getString(1));
                            objBranchSeccion.setE_idAccount(objseleccionado.getString(2));
                            objBranchSeccion.setE_externalCode(objseleccionado.getString(3));
                            objBranchSeccion.setE_code(objseleccionado.getString(4));
                            objBranchSeccion.setE_name(objseleccionado.getString(5));
                            objBranchSeccion.setE_neighborhood(objseleccionado.getString(7));
                            objBranchSeccion.setE_mainStreet(objseleccionado.getString(6));
                            objBranchSeccion.setE_reference(objseleccionado.getString(8));
                            objBranchSeccion.setE_propietario(objseleccionado.getString(9));
                            objBranchSeccion.setE_uriformulario(objseleccionado.getString(10));
                            objBranchSeccion.setE_idprovince(objseleccionado.getString(11));
                            objBranchSeccion.setE_iddistrict(objseleccionado.getString(12));
                            objBranchSeccion.setE_idParish(objseleccionado.getString(13));
                            objBranchSeccion.setE_rutaaggregate(String.valueOf(cmbnumeroruta.getSelectedItem().toString()));
                            objBranchSeccion.setE_imeI_ID(objseleccionado.getString(15));
                            objBranchSeccion.setE_nuevo("");
                                    Toast.makeText(getApplication(),
                                            "Iniciar Tarea  para: \n" + objseleccionado.getString(5),
                                            Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), FormChooserList.class);
                                    startActivity(i);
                        } while (objseleccionado.moveToNext());
                    }
                } else {
                    Toast.makeText(getApplication(),
                            "Seleccione Ruta", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void buscarlocalesrutaCodigo(String ruta, String formabusqueda) {
        category=new ArrayList<>();
        if (txtbuscar.getText().toString().equals("")) {
            Toast.makeText(getApplication(),
                    "Ingrese Datos", Toast.LENGTH_SHORT).show();
        } else {
            /*************************************** Validacion solo para censo*************************/
            if (ruta == "") {
                Toast.makeText(getApplication(),
                        "Seleccione Ruta", Toast.LENGTH_SHORT).show();
            } else {
                String opcion = "";
                String[] args = new String[]{};
                String where = "where 1=1 ";

                if (formabusqueda.equalsIgnoreCase("C")) {
                    opcion = "c";
                    args = new String[]{txtbuscar.getText().toString().toUpperCase() + "%"};
                    //*********************************************where solo para censo
                    where = "where 1=1 ";
                    where = where + " and  rutaaggregate ='" + ruta + "' ";
                    //*********************************************
                    where = where + "and code = '" + txtbuscar.getText().toString().toUpperCase() + "'";
                }
                if (formabusqueda.equalsIgnoreCase("N")) {
                    opcion = "n";
                    args = new String[]{txtbuscar.getText().toString().toUpperCase() + "%"};
                    //*********************************************where solo para censo
                    where = "where 1=1 ";
                    where = where + " and rutaaggregate =" + ruta + " ";
                    //*********************************************
                    where = where + "and name like '%" + txtbuscar.getText().toString().toUpperCase() + "%'";
                }
                Cursor cursor = objutil.ListarTareas(args, opcion, where);

                Branch objBranch = new Branch();
                ArrayList<String> listar = new ArrayList<String>();
                List<Branch> listOBJ = new ArrayList<Branch>();
                if (cursor.moveToFirst()) {

                    do {
                        listar.add(cursor.getString(4) + " " + cursor.getString(5));
                        objBranch = new Branch();
                        objBranch.setID(cursor.getString(0));
                        objBranch.setIdbranch(cursor.getString(1));
                        objBranch.setIdAccount(cursor.getString(2));
                        objBranch.setExternalCode(cursor.getString(3));
                        objBranch.setCode(cursor.getString(4));
                        objBranch.setName(cursor.getString(5));
                        objBranch.setMainStreet(cursor.getString(6));
                        objBranch.setNeighborhood(cursor.getString(7));
                        objBranch.setReference(cursor.getString(8));
                        objBranch.setPropietario(cursor.getString(9));
                        objBranch.setUriformulario(cursor.getString(10));
                        objBranch.setIdprovince(cursor.getString(11));
                        objBranch.setIddistrict(cursor.getString(12));
                        objBranch.setIdParish(cursor.getString(13));
                        objBranch.setRutaaggregate(cursor.getString(14));
                        objBranch.setImeI_ID(cursor.getString(15));
                        listOBJ.add(objBranch);
                        category.add(new Category(cursor.getString(1),cursor.getString(4),cursor.getString(5),null));

                    } while (cursor.moveToNext());
                }



                AdapterItem adapter = new AdapterItem(this, category);

                lv.setAdapter(adapter);

            }
        }

    }

    public void ListarRutasEngine() {
        Cursor cursor = objutil.ListarRutas();

        //startManagingCursor(cursor);
        ArrayList<String> listar = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                listar.add(String.valueOf(cursor.getString(0)));
            } while (cursor.moveToNext());
        }
        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(getApplication(), R.layout.spinner_personalizado, listar);
        cmbnumeroruta.setAdapter(adaptador);
    }

    public void buscarlocalesruta(String ruta, String formabusqueda) {
        category=new ArrayList<>();
        // if(txtbuscar.getText().toString().equals("")){
        Toast.makeText(getApplication(),
                "Ingrese Datos", Toast.LENGTH_SHORT).show();
        // }else {
        /*************************************** Validacion solo para censo*************************/
        if (ruta == "") {
            Toast.makeText(getApplication(),
                    "Seleccione Ruta", Toast.LENGTH_SHORT).show();
        } else {
            String opcion = "";
            String[] args = new String[]{};
            String where = "where 1=1 ";

            if (formabusqueda == "C") {
                opcion = "c";
                args = new String[]{txtbuscar.getText().toString().toUpperCase() + "%"};
                //*********************************************where solo para censo
                where = "where 1=1 ";
                where = where + " and  rutaaggregate ='" + ruta + "' ";
                //*********************************************
                //where = where + "and code = '" + txtbuscar.getText().toString().toUpperCase() + "'";
            }
            if (formabusqueda == "N") {
                opcion = "n";
                args = new String[]{txtbuscar.getText().toString().toUpperCase() + "%"};
                //*********************************************where solo para censo
                where = "where 1=1 ";
                where = where + " and rutaaggregate =" + ruta + " ";
                //*********************************************
                //where = where + "and name like '%" + txtbuscar.getText().toString().toUpperCase() + "%'";
            }
            Cursor cursor = objutil.ListarTareas(args, opcion, where);
            Branch objBranch = new Branch();
            ArrayList<String> listar = new ArrayList<String>();
            List<Branch> listOBJ = new ArrayList<Branch>();
            if (cursor.moveToFirst()) {

                do {
                    listar.add(cursor.getString(4) + " " + cursor.getString(5));
                    objBranch = new Branch();
                    objBranch.setID(cursor.getString(0));
                    objBranch.setIdbranch(cursor.getString(1));
                    objBranch.setIdAccount(cursor.getString(2));
                    objBranch.setExternalCode(cursor.getString(3));
                    objBranch.setCode(cursor.getString(4));
                    objBranch.setName(cursor.getString(5));
                    objBranch.setMainStreet(cursor.getString(6));
                    objBranch.setNeighborhood(cursor.getString(7));
                    objBranch.setReference(cursor.getString(8));
                    objBranch.setPropietario(cursor.getString(9));
                    objBranch.setUriformulario(cursor.getString(10));
                    objBranch.setIdprovince(cursor.getString(11));
                    objBranch.setIddistrict(cursor.getString(12));
                    objBranch.setIdParish(cursor.getString(13));
                    objBranch.setRutaaggregate(cursor.getString(14));
                    objBranch.setImeI_ID(cursor.getString(15));
                    listOBJ.add(objBranch);
                    category.add(new Category(cursor.getString(1),cursor.getString(4),cursor.getString(5),null));


                } while (cursor.moveToNext());
            }


            ArrayAdapter<Branch> adaptador;


            AdapterItem adapter = new AdapterItem(this, category);

            lv.setAdapter(adapter);

            //}
            //}
        }
    }
    public void CargarNuevos(){
        BaseDatosEngine usdbh = new BaseDatosEngine();
        usdbh = usdbh.open();
        Cursor cursor = usdbh.listarCodigois();
        String CodigoNuevo="";
        objBranchSeccion.setE_ID("");
        objBranchSeccion.setE_idbranch("");
        objBranchSeccion.setE_idAccount("");
        objBranchSeccion.setE_externalCode("");
        objBranchSeccion.setE_code("");
        objBranchSeccion.setE_neighborhood("");
        objBranchSeccion.setE_mainStreet("");
        objBranchSeccion.setE_reference("");
        objBranchSeccion.setE_propietario("");
        objBranchSeccion.setE_uriformulario("");
        objBranchSeccion.setE_idprovince("");
        objBranchSeccion.setE_iddistrict("");
        objBranchSeccion.setE_idParish("");
        objBranchSeccion.setE_rutaaggregate("0");
        objBranchSeccion.setE_imeI_ID("");

        objcodigoSession.setcId("");
        objcodigoSession.setC_idAccount("");
        objcodigoSession.setC_code("");
        objcodigoSession.setC_estado("");
        objcodigoSession.setC_uri("");
        objcodigoSession.setC_imei_id("");
        if(cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    objBranchSeccion.setE_ID("");
                    objBranchSeccion.setE_idbranch("");
                    objBranchSeccion.setE_idAccount("");
                    objBranchSeccion.setE_externalCode("");
                    objBranchSeccion.setE_code("");
                    objBranchSeccion.setE_neighborhood("");
                    objBranchSeccion.setE_mainStreet("");
                    objBranchSeccion.setE_reference("");
                    objBranchSeccion.setE_propietario("");
                    objBranchSeccion.setE_uriformulario("");
                    objBranchSeccion.setE_idprovince("");
                    objBranchSeccion.setE_iddistrict("");
                    objBranchSeccion.setE_idParish("");
                    objBranchSeccion.setE_rutaaggregate("0");
                    objBranchSeccion.setE_imeI_ID("");
                    Cursor cursor1 = usdbh.CodigoNuevo(cursor.getString(0));
                    if (cursor1.moveToFirst()) {
                        do {
                            objBranchSeccion.setE_code(cursor1.getString(6));
                            objcodigoSession.setcId(cursor1.getString(0));
                            objcodigoSession.setC_idAccount(cursor1.getString(1));
                            objcodigoSession.setC_code(cursor1.getString(2));
                            objcodigoSession.setC_estado(cursor1.getString(3));
                            objcodigoSession.setC_uri(cursor1.getString(4));
                            objcodigoSession.setC_imei_id(cursor1.getString(5));
                            objcodigoSession.setC_codeunico(cursor1.getString(6));
                            objBranchSeccion.setE_rutaaggregate(cmbnumeroruta.getSelectedItem().toString());
                            objBranchSeccion.setE_nuevo("nuevo");



                        } while (cursor.moveToNext());
                    }


                } while (cursor.moveToNext());
            }
        }
        usdbh.close();
    }
    public String  obterImeid() {
        String myIMEI = "0";
        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            myIMEI = mTelephony.getDeviceId();
        }
        return myIMEI;
    }
    public class CargarCodigosNuvos extends AsyncTask<Void,Void,String> {
        public CargarCodigosNuvos(Context context) {
            this.context = context;
        }
        private ProgressDialog progressDialog;
        private  Context context;
        @Override
        protected void onPreExecute() {
            // set up progress dialog
            try {
                progressDialog = new ProgressDialog(context);
                progressDialog.setIndeterminate(true);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please wait...");

                // show it
                progressDialog.show();
            }catch (Exception ex){

            }

        }
        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String jsonStr = null;

            try {
                // Construct the URL somehow
                String Idaccount =objcuentaSession.getCu_idAccount();
                String Idcampania="";
                String url1 = "http://mardisenginedesarrollo.azurewebsites.net/api/codigos?idAccounut="+Idaccount+"&imail="+obterImeid();
                URL url = new URL(url1);

                // Create the request to MuslimSalat.com, and open the connection

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();

                //JSONArray objetos = new JSONArray(result);
                respJSON = new JSONArray(jsonStr);
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }

            return jsonStr;
        }
        @Override
        protected void onPostExecute(String jsonString) {

            // dismiss the progress because downloading process is finished.
            progressDialog.dismiss();
            progress=new ProgressDialog(context);
            progress.setMessage("Descargando Codigos....");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // progress.setIndeterminate(true);
            progress.setCanceledOnTouchOutside(false);

            progress.setProgress(0);
            progress.setMax(respJSON.length());
            progress.show();
            final int totalProgressTime = respJSON.length();
            final Thread t = new Thread() {
                @Override
                public void run() {
                    int jumpTime = 0;
                    while(jumpTime < totalProgressTime) {
                        try {
                            JSONObject obj = respJSON.getJSONObject(jumpTime);
                            String id = obj.getString("id");
                            String idAccount = obj.getString("idAccount");
                            String code = obj.getString("code");
                            String estado = obj.getString("estado");
                            String uri = "";
                            String imei_id = obj.getString("imei_id");
                            String codeunico=obj.getString("codeunico");
                            BaseDatosEngine usdbh = new BaseDatosEngine();
                            try {

                                usdbh = usdbh.open();
                                ContentValues Objdatos = new ContentValues();
                                Objdatos.put(EstructuraBD.CabecerasCodigos.ID, id);
                                Objdatos.put(EstructuraBD.CabecerasCodigos.idAccount, idAccount);
                                Objdatos.put(EstructuraBD.CabecerasCodigos.code, code.toUpperCase());
                                Objdatos.put(EstructuraBD.CabecerasCodigos.estado, estado.toUpperCase());
                                Objdatos.put(EstructuraBD.CabecerasCodigos.uri, uri.toUpperCase());
                                Objdatos.put(EstructuraBD.CabecerasCodigos.imei_id, imei_id);
                                Objdatos.put(EstructuraBD.CabecerasCodigos.codeunico, codeunico);
                                usdbh.insertardatosCodigos(Objdatos);
                                usdbh.close();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            jumpTime += 1;
                            progress.setProgress(jumpTime);
                            sleep(25);
                        }
                        catch (InterruptedException e) {
                            Log.e("Error Carga",e.getMessage());
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    CargarNuevos();
                    if (objcodigoSession.getcId() != "") {
                        BaseDatosEngine usdbh = new BaseDatosEngine();
                        Cursor cursor = usdbh.RutaLista();
                        objBranchSeccion.setE_rutaaggregate("0");
                        if(cursor!=null) {
                            if (cursor.moveToFirst()) {

                                do {
                                    objBranchSeccion.setE_rutaaggregate(cursor.getString(0));
                                    objBranchSeccion.setE_nuevo("nuevo");

                                } while (cursor.moveToNext());


                            }
                        }



                        Intent intent = new Intent(getApplication(), FormChooserList.class);
                        startActivityForResult(intent, 0);
                    } else {
                        Toast.makeText(getApplication(),
                                "No se pudo cargar los códigos nuevos", Toast.LENGTH_SHORT).show();
                    }
                    progress.dismiss();
                }
            };
            t.start();
            //progress.dismiss();



        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Collect.getInstance().getActivityLogger().logOnStart(this);
    }

    @Override
    protected void onStop() {
        Collect.getInstance().getActivityLogger().logOnStop(this);
        super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Collect.getInstance().getActivityLogger()
                .logAction(this, "onCreateOptionsMenu", "show");
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cuentaconf:
                Collect.getInstance()
                        .getActivityLogger()
                        .logAction(this, "onOptionsItemSelected",
                                "MENU_CUENTACONF");
                Intent menuconf= new Intent(this, configuracion.class);
                startActivity(menuconf);
                return true;
            case R.id.menu_main:
                Collect.getInstance()
                        .getActivityLogger()
                        .logAction(this, "onOptionsItemSelected",
                                "MENU_MAIN");
                startActivity(new Intent(getBaseContext(), MainMenuActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return true;
            case R.id.menu_about:
                Collect.getInstance()
                        .getActivityLogger()
                        .logAction(this, "onOptionsItemSelected",
                                "MENU_ABOUT");
                Intent aboutIntent = new Intent(this, AboutPreferencesActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.menu_general_preferences:
                Collect.getInstance()
                        .getActivityLogger()
                        .logAction(this, "onOptionsItemSelected",
                                "MENU_PREFERENCES");
                Intent ig = new Intent(this, PreferencesActivity.class);
                startActivity(ig);
                return true;
            case R.id.menu_admin_preferences:
                Collect.getInstance().getActivityLogger()
                        .logAction(this, "onOptionsItemSelected", "MENU_ADMIN");
                String pw = adminPreferences.getString(AdminKeys.KEY_ADMIN_PW, "");
                if ("".equalsIgnoreCase(pw)) {
                    Intent i = new Intent(getApplicationContext(),
                            AdminPreferencesActivity.class);
                    startActivity(i);
                } else {
                    showDialog(PASSWORD_DIALOG);
                    Collect.getInstance().getActivityLogger()
                            .logAction(this, "createAdminPasswordDialog", "show");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                AdminPreferencesActivity.ADMIN_PREFERENCES, 0);

        boolean edit = sharedPreferences.getBoolean(
                AdminKeys.KEY_EDIT_SAVED, true);


        boolean send = sharedPreferences.getBoolean(
                AdminKeys.KEY_SEND_FINALIZED, true);


        boolean viewSent = sharedPreferences.getBoolean(
                AdminKeys.KEY_VIEW_SENT, true);






        ((Collect) getApplication())
                .getDefaultTracker()
                .enableAutoActivityTracking(true);
    }
}