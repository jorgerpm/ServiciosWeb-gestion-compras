/*
 * Proyecto API Rest (ServiciosWeb)
 * IdebSystems Cia. Ltda. Derechos reservados. 2022
 * Prohibida la reproducción total o parcial de este producto
 */
package com.idebsystems.serviciosweb.servicio;

import com.idebsystems.serviciosweb.dao.ParametroDAO;
import com.idebsystems.serviciosweb.entities.Parametro;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author jorge
 */
public class CorreoServicio {

    public void enviarUrlNuevaClave(String correo) {
        try {
            //consultar los prametros del correo desde la base de datos.
            ParametroDAO paramDao = new ParametroDAO();
            List<Parametro> listaParams = paramDao.listarParametros();

            List<Parametro> paramsMail = listaParams.stream().filter(p -> p.getNombre().contains("MAIL")).collect(Collectors.toList());
            //findAny().orElse(null);

            Parametro paramHost = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("HOSTMAIL")).findAny().get();
            Parametro paramPuerto = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("PUERTOMAIL")).findAny().get();
            Parametro paramUserMail = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("USERMAIL")).findAny().get();
            Parametro paramClaveMail = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("CLAVEMAIL")).findAny().get();
            
            Parametro paramNomRemit = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("REMITENTEMAIL_RC")).findAny().get();
            Parametro paramSubect = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("ASUNTOMAIL_RC")).findAny().get();
            Parametro paramMsm = paramsMail.stream().filter(p -> p.getNombre().equalsIgnoreCase("MENSAJEMAIL_RC")).findAny().get();

            String host = paramHost.getValor(); //"smtp.office365.com";
            int port = Integer.parseInt(paramPuerto.getValor()); //587;
            String userName = paramUserMail.getValor();// "jorgep_m@hotmail.com";
            String password = paramClaveMail.getValor();

            Properties properties = new Properties();
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.user", userName);
            properties.put("mail.password", password);

            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            };
            Session session = Session.getInstance(properties, auth);

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName, paramNomRemit.getValor()));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(correo, ""));
            msg.setSubject(paramSubect.getValor());
            msg.setText(paramMsm.getValor());
            Transport.send(msg);
            
        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        } catch (UnsupportedEncodingException e) {
            // ...
        } catch (Exception e) {
            // ...
        }

    }
}
