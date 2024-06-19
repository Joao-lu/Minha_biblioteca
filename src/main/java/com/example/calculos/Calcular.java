package com.example.calculos;

public class Calcular {

    public Calcular(){}
    public static double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        // Raio da Terra em quilômetros
        final double raioTerra = 6371.0;

        // Converte as latitudes e longitudes para radianos
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);

        // Calcula a diferença de latitude e longitude em radianos
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2)
                * Math.cos(radLat1) * Math.cos(radLat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcula a distância usando a fórmula de Haversine
        double distancia = raioTerra * c*1000;
        return distancia;
    }
}
