package utils.validators;

public class Name {
    private String name;
    private String error = null;

    public Name(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        if (isEmpty())
            error = "Nome nao pode ficar vazio.";
        else if (!validateNameSize())
            error = "Nome não pode ter mais do que 255 caracteres.";

        return error == null;
    }

    public String getError() {
        return error == null ? "Sem erros" : error;
    }

    private boolean isEmpty() {
        return name.isEmpty() || name.isBlank();
    }

    private boolean validateNameSize() {
        return name.length() <= 255;
    }
}
