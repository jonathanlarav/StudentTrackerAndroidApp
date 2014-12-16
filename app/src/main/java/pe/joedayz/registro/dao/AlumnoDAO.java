package pe.joedayz.registro.dao;

import java.util.ArrayList;
import java.util.List;

import pe.joedayz.registro.model.Alumno;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlumnoDAO extends SQLiteOpenHelper {

	private static final String DATABASE = "RegistroJoeDayz";
	private static final int VERSION = 1;

	public AlumnoDAO(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	public void guardar(Alumno alumno) {
		ContentValues values = new ContentValues();
		values.put("nombre", alumno.getNombre());
		values.put("site", alumno.getSite());
		values.put("direccion", alumno.getDireccion());
		values.put("telefono", alumno.getTelefono());
		values.put("foto", alumno.getFoto());
		values.put("nota", alumno.getNota());

		getWritableDatabase().insert("Alumnos", null, values);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "CREATE TABLE Alumnos (id INTEGER PRIMARY KEY, "
				+ "nombre TEXT UNIQUE NOT NULL," + "telefono TEXT,"
				+ "direccion TEXT," + "site TEXT," + "foto TEXT,"
				+ "nota REAL)";
		db.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String ddl = "DROP TABLE IF EXISTS Alumnos";
		db.execSQL(ddl);
		onCreate(db);
	}

	public List<Alumno> getLista() {
		String[] columns = {"id", "nombre", "site", "direccion", "telefono", "foto", "nota"};
		Cursor cursor = getWritableDatabase().query("Alumnos", columns , null, null, null, null, null);
		boolean moveToNext = cursor.moveToNext();
		
		List<Alumno> lista = new ArrayList<Alumno>();
		while (moveToNext) {
			Alumno alumno = new Alumno();
			alumno.setId(cursor.getLong(0));
			alumno.setNombre(cursor.getString(1));
			alumno.setSite(cursor.getString(2));
			alumno.setDireccion(cursor.getString(3));
			alumno.setTelefono(cursor.getString(4));
			alumno.setFoto(cursor.getString(5));
			alumno.setNota(cursor.getDouble(6));			
			lista.add(alumno);
			moveToNext = cursor.moveToNext();
		}		
		return lista;
	}

	public void eliminar(Alumno alumno) {
		String[] args = {alumno.getId().toString()};
		getWritableDatabase().delete("Alumnos", "id=?", args);
	}

	public void modificar(Alumno alumno) {
		ContentValues values = new ContentValues();
		values.put("nombre", alumno.getNombre());
		values.put("site", alumno.getSite());
		values.put("direccion", alumno.getDireccion());
		values.put("telefono", alumno.getTelefono());
		values.put("foto", alumno.getFoto());
		values.put("nota", alumno.getNota());
		
		String[] args = {alumno.getId().toString()};
		getWritableDatabase().update("Alumnos", values, "id=?", args);
	}

	public boolean isAlumno(String telefono) {
		String args[] = {telefono};
		Cursor cursor = getWritableDatabase().rawQuery("SELECT id FROM Alumnos WHERE telefono=?", args);
		return cursor.moveToFirst();
	}
}