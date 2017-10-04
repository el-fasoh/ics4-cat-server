package com.strathmore.ics3.cat.domain;

import java.util.List;

public class Calculator {

    public static Double multiply(List<Double> operands) {
        double result = 1;
        for (Double operand : operands) {
            result = operand * result;
        }
        return result;
    }

    public static Double add(List<Double>operands){
        double result = 0;
        for (Double operand : operands) {
            result = operand + result;
        }
        return result;
    }
}
