package com.example.parcial_1_am_acn4b_carugati_fattore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    TextView resultado;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);


    }

    public void calcular(View view) {
        Button boton = (Button) view;
        String textoBoton = boton.getText().toString();
        String concatenar = resultado.getText().toString() + textoBoton;
        String concatenarSinCeros = quitarCerosIzquierda(concatenar);

        if (textoBoton.equals("=")) {
            double result = 0.0;
            try {
                result = eval(resultado.getText().toString());
                resultado.setText(Double.toString(result));
            } catch (Exception e) {
                resultado.setText(e.toString());
            }
        } else if (textoBoton.equals("RESETEAR")) {
            resultado.setText("0");
        } else {
            resultado.setText(concatenarSinCeros);
        }
    }

    private String quitarCerosIzquierda(String str) {
        int i = 0;
        while (i < str.length() && str.charAt(i) == '0') {
            i++;
        }
        StringBuilder sb = new StringBuilder(str);
        sb.replace(0, i, "");
        return sb.toString();
    }
    private double eval(String str) {
        return new Object() {
            int pos = -1;
            int ch = 0;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
                double x;
                int startPos = pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if (ch >= '0' && ch <= '9' || ch == '.') { // numbers
                    while (ch >= '0' && ch <= '9' || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                return x;
            }
        }.parse();
    }
    public void acalculadora(View view) {
        setContentView(R.layout.activity_main);
        resultado = findViewById(R.id.resultado);
    }

    public void volvermenu(View view) {
        setContentView(R.layout.menu);
    }

    public void aRelojes(View view){
        setContentView(R.layout.relojes);

        // Obtenemos la hora local
        ZonedDateTime horaLocal = ZonedDateTime.now();
        // Convertimos la hora local a formato hh:mm
        LocalTime horaLocal24hs = horaLocal.toLocalTime();
        // Formateamos la hora en formato hh:mm
        DateTimeFormatter formatterLocal = DateTimeFormatter.ofPattern("HH:mm");
        String horaLocalFormateada = horaLocal24hs.format(formatterLocal);
        // Mostramos la hora
        ((TextView) findViewById(R.id.textView1)).setText("Hora local: " + horaLocalFormateada);

        // Obtenemos la hora en Nueva York
        ZonedDateTime horaNuevaYork = ZonedDateTime.now(ZoneId.of("America/New_York"));
        // Convertimos la hora en Nueva York a formato hh:mm
        LocalTime horaNuevaYork24hs = horaNuevaYork.toLocalTime();
        // Formateamos la hora en Nueva York en formato hh:mm
        DateTimeFormatter formatterNY = DateTimeFormatter.ofPattern("HH:mm");
        String horaNuevaYorkFormateada = horaNuevaYork24hs.format(formatterNY);
        // Mostramos la hora en Nueva York
        ((TextView) findViewById(R.id.textView2)).setText("Hora en Nueva York: " + horaNuevaYorkFormateada);

        // Obtenemos la hora en Londres
        ZonedDateTime horaLondres = ZonedDateTime.now(ZoneId.of("Europe/London"));
        // Convertimos la hora en Londres a formato hh:mm
        LocalTime horaLondres24hs = horaLondres.toLocalTime();
        // Formateamos la hora en Londres en formato hh:mm
        DateTimeFormatter formatterLon = DateTimeFormatter.ofPattern("HH:mm");
        String horaLondresFormateada = horaLondres24hs.format(formatterLon);
        // Mostramos la hora en Londres
        ((TextView) findViewById(R.id.textView3)).setText("Hora en Londres: " + horaLondresFormateada);

        // Obtenemos la hora en Tokio
        ZonedDateTime horaTokio = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        // Convertimos la hora en Tokio a formato hh:mm
        LocalTime horaTokio24hs = horaTokio.toLocalTime();
        // Formateamos la hora en Tokio en formato hh:mm
        DateTimeFormatter formatterTok = DateTimeFormatter.ofPattern("HH:mm");
        String horaTokioFormateada = horaTokio24hs.format(formatterTok);
        // Mostramos la hora en Tokio
        ((TextView) findViewById(R.id.textView4)).setText("Hora en Tokio: " + horaTokioFormateada);


    }
}