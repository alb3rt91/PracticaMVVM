package com.example.practicamvvm;

public class SimuladorHipoteca {

    public static class Solicitud {
        public double capital;
        public int plazo;

        public Solicitud(double capital, int plazo) {
            this.capital = capital;
            this.plazo = plazo;
        }
    }

    interface Callback {
        void cuandoEsteCalculadaLaCuota(double cuota);

        void cuandoHayaErrorDeCapitalInferiorAlMinimo(double capitalMinimo);

        void cuandoHayaErrorDePlazoInferiorAlMinimo(int plazoMinimo);

        void cuandoEmpieceElCalculo();

        void cuandoFinaliceElCalculo();
    }

    public void calcular(Solicitud solicitud, Callback callback) {
        double interes = 0;
        double capitalMinimo = 0;
        int plazoMinimo = 0;

        // Notificar que el cálculo está comenzando
        callback.cuandoEmpieceElCalculo();

        try {
            Thread.sleep(2500);  // Simula una operación larga
            interes = 0.01605;
            capitalMinimo = 1000;
            plazoMinimo = 2;
        } catch (InterruptedException e) {
            // Manejo de la excepción, si es necesario
        }

        boolean error = false;

        if (solicitud.capital < capitalMinimo) {
            callback.cuandoHayaErrorDeCapitalInferiorAlMinimo(capitalMinimo);
            error = true;
        }

        if (solicitud.plazo < plazoMinimo) {
            callback.cuandoHayaErrorDePlazoInferiorAlMinimo(plazoMinimo);
            error = true;
        }

        if (!error) {
            double cuota = solicitud.capital * interes / 12 / (1 - Math.pow(1 + (interes / 12), -solicitud.plazo * 12));
            callback.cuandoEsteCalculadaLaCuota(cuota);
        }

        // Notificar que el cálculo ha finalizado
        callback.cuandoFinaliceElCalculo();
    }
}




















