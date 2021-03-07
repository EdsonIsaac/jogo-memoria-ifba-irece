package br.edu.ifba.jogomemoria.infrastructure.suport;

public class StringUtil {

    public boolean isNull (final String str) {
        return (str == null);
    }

    public boolean isEmpty (final String str) {
        return (isNull(str) ? true : (str.trim().equals("")));
    }

    public boolean isNullAndEmpty(final String str) {
        return (isNull(str) && isEmpty(str));
    }

    public boolean isNullOrEmpty(final String str) {
        return (isNull(str) || isEmpty(str));
    }
}