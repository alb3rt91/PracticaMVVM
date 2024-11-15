package com.example.practicamvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MiHipotecaViewModel extends AndroidViewModel {

    Executor executor;

    SimuladorHipoteca simulador;

    MutableLiveData<Double> cuota = new MutableLiveData<>();
    MutableLiveData<Double> errorCapital = new MutableLiveData<>();
    MutableLiveData<Integer> errorPlazos = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();

    public MiHipotecaViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorHipoteca();
    }

    public void calcular(double capital, int plazo) {

        final SimuladorHipoteca.Solicitud solicitud = new SimuladorHipoteca.Solicitud(capital, plazo);

        // Indicar que empieza el cálculo
        calculando.postValue(true);

        executor.execute(new Runnable() {
            @Override
            public void run() {

                simulador.calcular(solicitud, new SimuladorHipoteca.Callback() {
                    @Override
                    public void cuandoEsteCalculadaLaCuota(double cuotaResultante) {
                        errorCapital.postValue(null);
                        errorPlazos.postValue(null);
                        cuota.postValue(cuotaResultante);

                        // Indicar que el cálculo ha finalizado
                        calculando.postValue(false);
                    }

                    @Override
                    public void cuandoHayaErrorDeCapitalInferiorAlMinimo(double capitalMinimo) {
                        errorCapital.postValue(capitalMinimo);

                        // Indicar que el cálculo ha finalizado
                        calculando.postValue(false);
                    }

                    @Override
                    public void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazoMinimo) {
                        errorPlazos.postValue(plazoMinimo);

                        // Indicar que el cálculo ha finalizado
                        calculando.postValue(false);
                    }

                    @Override
                    public void cuandoEmpieceElCalculo() {

                    }

                    @Override
                    public void cuandoFinaliceElCalculo() {

                    }
                });
            }
        });
    }
}
