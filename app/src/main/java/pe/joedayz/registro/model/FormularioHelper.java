package pe.joedayz.registro.model;

import pe.joedayz.registro.Formulario;
import pe.joedayz.registro.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class FormularioHelper {

	private EditText nombre;
	private EditText site;
	private EditText telefono;
	private EditText direccion;
	private RatingBar nota;
	private ImageView foto;
	private Alumno alumno;

	public FormularioHelper(Formulario formulario) {
		nombre = (EditText) formulario.findViewById(R.id.nombre);
		site = (EditText) formulario.findViewById(R.id.site);
		telefono = (EditText) formulario.findViewById(R.id.telefono);
		direccion = (EditText) formulario.findViewById(R.id.direccion);	
		nota = (RatingBar) formulario.findViewById(R.id.nota);
		
		foto = (ImageView)formulario.findViewById(R.id.foto);
		alumno = new Alumno();
	}

	public Alumno guardarAlumno() {
		alumno.setNombre(nombre.getText().toString());
		alumno.setSite(site.getText().toString());
		alumno.setTelefono(telefono.getText().toString());
		alumno.setDireccion(direccion.getText().toString());
		alumno.setNota(Double.valueOf(nota.getRating()));
		return alumno;
	}

	public void colocarAlumnoEnFormulario(Alumno alumno) {
		this.alumno = alumno;
		nombre.setText(alumno.getNombre());
		site.setText(alumno.getSite());
		telefono.setText(alumno.getTelefono());
		direccion.setText(alumno.getDireccion());
		nota.setRating(alumno.getNota().floatValue());
		if(alumno.getFoto() != null){
			foto.setImageURI(Uri.parse(alumno.getFoto()));			
		}
	}

	public ImageView getFoto() {
		return foto;
	}

	public void cargarImagen(String rutaArchivo) {
		alumno.setFoto(rutaArchivo);
		
		Bitmap imagen = BitmapFactory.decodeFile(rutaArchivo);
		Bitmap imagenReducida = Bitmap.createScaledBitmap(imagen, 100, 100, true);
		
		foto.setImageBitmap(imagenReducida);
	}

}
