package pe.joedayz.registro.background;

import pe.joedayz.registro.R;
import pe.joedayz.registro.dao.AlumnoDAO;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Object[] mensajes = (Object[]) intent.getExtras().get("pdus");
		byte[] primero = (byte[]) mensajes[0];

		SmsMessage sms = SmsMessage.createFromPdu(primero);
		String telefono = sms.getDisplayOriginatingAddress();

		Toast.makeText(context, "sms desde numero " + telefono,
				Toast.LENGTH_LONG).show();

		AlumnoDAO alumnoDAO = new AlumnoDAO(context);
		boolean esAlumno = alumnoDAO.isAlumno(telefono);
		alumnoDAO.close();

		if (esAlumno) {
			MediaPlayer player = MediaPlayer.create(context, R.raw.main);
			player.start();
			Toast.makeText(context, "tocando musica ", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
