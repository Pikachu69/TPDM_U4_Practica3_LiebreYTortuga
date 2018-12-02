package mx.edu.ittepic.tpdm_u4_practica3_liebretortuga;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
ImageView liebre, tortuga;
Button iniciar;
float contadorLiebre, contadorTortuga;
int posicionLiebre, posicionTortuga;
Thread hiloLiebre, hiloTortuga;
Boolean finalizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        liebre = findViewById(R.id.liebre);
        tortuga = findViewById(R.id.tortuga);

        iniciar = findViewById(R.id.iniciar);
        posicionLiebre=1;
        posicionTortuga=1;

        finalizado = true;

        contadorLiebre = 0;
        contadorTortuga = 0;

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    hiloTortuga.start();
                    hiloLiebre.start();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,"Carrera en curso",Toast.LENGTH_SHORT).show();
                }
                //iniciar.setEnabled(false);
            }
        });

        hiloTortuga = new Thread() {
            @Override
            public void run() {
                while (finalizado) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int n = accion();
                            int m = avance(2,n);
                            if (m==1){
                                contadorTortuga+=21.5;
                                posicionTortuga++;
                                tortuga.setTranslationY(contadorTortuga);
                                ganador(2);
                            } else {
                                for (int i=1;i<=m;i++){
                                    if (m==3){
                                        contadorTortuga+=21.5;
                                        posicionTortuga++;
                                        tortuga.setTranslationY(contadorTortuga);
                                        ganador(2);
                                    }else if (m==6){
                                        if (posicionTortuga<=1){
                                            break;
                                        } else {
                                            contadorTortuga -= 21.5;
                                            posicionTortuga--;
                                            tortuga.setTranslationY(contadorTortuga);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        hiloLiebre = new Thread(){
            @Override
            public void run() {
                while (finalizado){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int n = accion();
                            int m = avance(1,n);
                            if (m==1){
                                contadorLiebre+=21.5;
                                posicionLiebre++;
                                liebre.setTranslationY(contadorLiebre);
                                ganador(1);
                            }

                            else if (m==0){
                                try {
                                    sleep(900);
                                } catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                            }

                            else {
                                for (int i=1;i<=m;i++){
                                    if (m==9){
                                        contadorLiebre+=21.5;
                                        posicionLiebre++;
                                        liebre.setTranslationY(contadorLiebre);
                                        ganador(1);
                                    } else if (m==12){
                                        if (posicionLiebre<=1){
                                            break;
                                        }else {
                                            contadorLiebre -= 21.5;
                                            posicionLiebre--;
                                            liebre.setTranslationY(contadorLiebre);
                                        }
                                    } else if (m==2){
                                        contadorLiebre+=21.5;
                                        posicionLiebre++;
                                        liebre.setTranslationY(contadorLiebre);
                                        ganador(1);
                                    }
                                }
                            }
                        }
                    });
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void ganador(int a) {
        switch (a) {
            case 1:
                if (posicionLiebre==70){
                    finalizado = false;
                    mensaje("Liebre");
                }
                break;
            case 2:
                if (posicionTortuga==70){
                    finalizado = false;
                    mensaje("Tortuga");
                }
                break;
        }
    }

    private void mensaje(String ganador) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setTitle("Carrera Finalizada")
                .setMessage("a Ganado la "+ganador)
                .setPositiveButton("Aceptar",null)
                .show();
    }

    private int avance(int a, int n) {
        int r = 0;
        switch (a){
            case 1:
                if (n>=1 && n<=20) {
                    r = 9;
                    break;
                }
                if (n>=21 && n<=30) {
                    r = 12;
                    break;
                }
                if (n>=31 && n<=60) {
                    r = 1;
                    break;
                }
                if (n>=61 && n<=80) {
                    r = 2;
                    break;
                }
                if (n>80) {
                    r = 0;
                    break;
                }

            case 2:
                if (n>=1 && n<=50) {
                    r = 3;
                    break;
                }
                if (n>=51 && n<=70) {
                    r = 6;
                    break;
                }
                if (n>70) {
                    r = 1;
                    break;
                }
        }
        return r;
    }

    private int accion() {
        return (int)(Math.random()*100)+1;
    }
}
