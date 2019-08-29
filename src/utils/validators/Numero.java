package utils.validators;

public class Numero {
    private String numero;
    private String error = null;

    public Numero(String numero) {
        setNumero(numero);
    }

    public void setNumero(String numero) {
        this.numero = numero;
        clearString();
    }

    private void clearString() {
        this.numero = this.numero.replace(",", ".");
    }

    public boolean isValid() {
        if (!countDotsValids())
            error = "Numero so pode ter 1 virgula ou ponto.";
        else if (!isNumeric())
            error = "Numero só pode conter números e virgula ou ponto";

        return error == null;
    }

    public String getError() {
        return error == null ? "Sem erros" : error;
    }

    private boolean countDotsValids() {
        var cont = 0;
        for (var index = 0; index < numero.length(); index++) {
            if (numero.charAt(index) == '.')
                cont ++;

            if (cont == 2)
                return false;
        }
        return true;
    }

    private boolean isNumeric() {
        for (int index = 0; index < numero.length(); index++) {
            char digit = numero.charAt(index);
            if ('0' <= digit && digit <= '9' || digit == '.')
                continue;
            else
                return false;
        }
        return true;
    }
}
