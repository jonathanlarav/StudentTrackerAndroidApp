package pe.joedayz.registro;

import java.util.List;

import pe.joedayz.registro.adapter.ListaAlumnosAdapter;
import pe.joedayz.registro.dao.AlumnoDAO;
import pe.joedayz.registro.model.Alumno;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ListaAlumnos extends Activity {

	private ListView lista;
	private Alumno alumno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listade_alumnos);

		lista = (ListView) findViewById(R.id.lista);
		
		registerForContextMenu(lista);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Alumno alumno = (Alumno) parent.getItemAtPosition(position);
				
				Intent irAFormulario = new Intent(ListaAlumnos.this, Formulario.class);
				irAFormulario.putExtra("alumno", alumno);
				startActivity(irAFormulario);
//				Toast.makeText(ListaAlumnos.this,
//						"click en la posicion " + position, Toast.LENGTH_SHORT)
//						.show();
			}
		});

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				alumno = (Alumno)adapter.getItemAtPosition(position);
				return false;
			}

		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuItem llamarMenu = menu.add("Llamar");
		llamarMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irATelefono = new Intent(Intent.ACTION_CALL);
				Uri data = Uri.parse("tel:" + alumno.getTelefono());
				irATelefono.setData(data);
				startActivity(irATelefono);
				return false;
			}
		});
		
		menu.add("Enviar SMS");
		
		MenuItem siteMenu = menu.add("Visitar sitio web");
		siteMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaSite = new Intent(Intent.ACTION_VIEW);
				Uri data = Uri.parse("http://"+alumno.getSite());
				irParaSite.setData(data);
				startActivity(irParaSite);
				return false;
			}
		});
		
		MenuItem eliminarMenu = menu.add("Eliminar");
		eliminarMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AlumnoDAO alumnoDAO = new AlumnoDAO(ListaAlumnos.this);
				alumnoDAO.eliminar(alumno);
				alumnoDAO.close();
				cargarLista();
				return false;
			}
		});
		
		menu.add("Ver en el mapa");
		menu.add("Enviar un email");
	}
	
	private void cargarLista() {
		AlumnoDAO alumnoDAO = new AlumnoDAO(this);
		
		List<Alumno> alumnos = alumnoDAO.getLista();
		alumnoDAO.close();
		
//		int layout = android.R.layout.simple_list_item_1;
//		ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(this, layout, listaAlumnos);
		
		ListaAlumnosAdapter adapter = new ListaAlumnosAdapter(alumnos, this);
		
		lista.setAdapter(adapter);		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		cargarLista();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lista_alumnos, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.nuevo:
			Intent irAFormulario = new Intent(this, Formulario.class);
			startActivity(irAFormulario);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
