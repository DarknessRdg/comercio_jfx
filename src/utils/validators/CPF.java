package utils.validators;


public class CPF {
    String  cpf;
    private final int CPF_SIZE = 11;
    private String error = null;

    public CPF(String cpf) {
        setCpf(cpf);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
        clearString();
    }

    public boolean isValid() {
        if (!isCorrectSize())
            error = "CPF deve ter exatamente 11 dígitos.";
        else if (!isNumeric())
            error = "CPF tem que ser numérico.";
        else if (!validateDigits())
            error = "CPF inválido.";

        return error == null;
    }

    public String getError() {
        return error == null ? "Sem erros" : error;
    }

    private void clearString() {
        cpf = cpf.replace("-", "");
        cpf = cpf.replace(".", "");
        cpf = cpf.trim();
    }

    private boolean validateDigits() {
        var firstDigitValid = calculateFirstDigit() == Character.getNumericValue(cpf.charAt(9));
        var secondDigitValid = calculateSecondDigit() == Character.getNumericValue(cpf.charAt(10));
        return firstDigitValid && secondDigitValid;
    }

    private int calculateFirstDigit() {
        var multiplicador = 10;
        var calculo = 0;
        for (int index = 0; index < CPF_SIZE - 2; index++) {
            calculo += Character.getNumericValue(cpf.charAt(index)) * multiplicador;
            multiplicador --;
        }

        int valor_eperado;
        var resto = calculo % 11;

        if (resto < 2)
            valor_eperado = 0;
        else
            valor_eperado = 11 - resto;

        return valor_eperado;
    }

    private int calculateSecondDigit() {
        var primeiro_digito = Character.getNumericValue(cpf.charAt(9));
        var multiplicador = 11;
        var calculo = 0;

        for (int index = 0; index < CPF_SIZE - 2; index++) {
            calculo += Character.getNumericValue(cpf.charAt(index)) * multiplicador;
            multiplicador --;
        }
        calculo += primeiro_digito * multiplicador;

        int valor_esperado;
        var resto = calculo % 11;

        if (resto < 2)
            valor_esperado = 0;
        else
            valor_esperado = 11 - resto;
        return valor_esperado;
    }

    private boolean isCorrectSize() {
        return cpf.length() == CPF_SIZE;
    }

    private boolean isNumeric() {
        for (int index = 0; index < CPF_SIZE; index++) {
            if ('0' <= cpf.charAt(index) && cpf.charAt(index) <= '9')
                continue;
            else
                return false;
        }
        return true;
    }
}
