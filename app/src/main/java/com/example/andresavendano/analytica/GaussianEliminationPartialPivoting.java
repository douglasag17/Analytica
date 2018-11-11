package com.example.andresavendano.analytica;

public class GaussianEliminationPartialPivoting {


    public double[] gaussianElimination(double[][] A, double[] b) {

        int n = A.length;
        double Ab[][] = augmentMatrix(A, b);
        for(int k = 0; k < n-1; k++) {
            Ab = partialPivoting(Ab, n, k);
            for(int i = k+1; i < n; i++) {
                double mult = Ab[i][k]/Ab[k][k];
                for(int j = k; j < n+1; j++) {
                    Ab[i][j] -=  mult*Ab[k][j];
                }
            }
        }
        double x[] = substitution(Ab, Ab.length);

        return x;
    }

    public static double[][] partialPivoting(double Ab[][], int n, int k) {
        double maxi = Math.abs(Ab[k][k]);
        int maxiRow = k;
        for(int s = k; s < n; s++) {
            if(Math.abs(Ab[s][k]) > maxi) {
                maxi = Math.abs(Ab[s][k]);
                maxiRow = s;
            }
        }
        if (maxi == 0) {
            System.out.println("The system has no solution");
        } else {
            if(maxiRow != k) {
                Ab = exchangeRows(Ab, maxiRow, k);
                return Ab;
            }
        }
        return Ab;
    }

    public static double[][] exchangeRows(double [][]Ab, int maxiRow, int k) {
        double aux;
        for(int i = 0; i < Ab[0].length; i++) {
            aux = Ab[k][i];
            Ab[k][i] = Ab[maxiRow][i];
            Ab[maxiRow][i] = aux;
        }
        return Ab;
    }

    public double[] substitution(double Ab[][], int n){
        double x[] = new double[n];

        x[n-1] = Ab[n-1][n]/Ab[n-1][n-1];
        for(int i = n-1; i > 0; i--){
            double sum = 0;
            for(int p = i + 1; p < n + 1; p++){
                sum += Ab[i-1][p-1]*x[p-1];
            }
            x[i-1] = (Ab[i-1][n]-sum)/(Ab[i-1][i-1]);

        }
        return x;
    }

    public double[][] augmentMatrix(double A[][], double b[]){
        double result[][] = new double[A.length][A.length+1];
        for(int i = 0; i < result.length; i++){
            for(int j = 0; j < result[0].length; j++){
                if(j < A.length){
                    result[i][j] = A[i][j];
                }else{
                    result[i][j] = b[i];
                }
            }
        }
        return result;
    }
}