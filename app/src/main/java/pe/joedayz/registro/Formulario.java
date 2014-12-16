package pe.joedayz.registro;

import java.io.File;

import pe.joedayz.registro.dao.AlumnoDAO;
import pe.joedayz.registro.model.Alumno;
import pe.joedayz.registro.model.FormularioHelper;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class Formulario extends Activity {

	private FormularioHelper formularioHelper;
	private String rutaArchivo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		Intent intent = getIntent();
		final Alumno alumnoAModificar = (Alumno) intent.getSerializableExtra("alumno");
		
//		Toast.makeText(this, "alumno " + alumno, Toast.LENGTH_SHORT).show();
		formularioHelper = new FormularioHelper(this);
		Button grabar = (Button)findViewById(R.id.boton);
		
		if(alumnoAModificar != null){
			grabar.setText("Modificar");
			formularioHelper.colocarAlumnoEnFormulario(alumnoAModificar);
		}
		
		grabar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Alumno alumno = formularioHelper.guardarAlumno();
				alumno.setFoto(rutaArchivo);
				AlumnoDAO alumnoDao = new AlumnoDAO(Formulario.this);
				
				if(alumnoAModificar == null) {
					alumnoDao.guardar(alumno);
				}else {
					alumno.setId(alumnoAModificar.getId());
					alumnoDao.modificar(alumno);
				}
				
				alumnoDao.close();
				
				finish();
			}
		});
		
		ImageView foto = (ImageView)formularioHelper.getFoto();
		foto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent irParaCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				rutaArchivo = Environment.getExternalStorageDirectory().toString()
						+ "/" + System.currentTimeMillis() + ".png";
				File archivo = new File(rutaArchivo);
				Uri imagenLocal = Uri.fromFile(archivo);
				
				irParaCamara.putExtra(MediaStore.EXTRA_OUTPUT, imagenLocal);
				
				startActivityForResult(irParaCamara, 123);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 123){
			if(resultCode == Activity.RESULT_OK){
				formularioHelper.cargarImagen(rutaArchivo);
			}else {
				rutaArchivo = null;
			}
		}
	}
}
