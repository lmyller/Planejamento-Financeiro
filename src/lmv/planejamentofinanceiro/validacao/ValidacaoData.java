package lmv.planejamentofinanceiro.validacao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lmv.planejamentofinanceiro.interfaces.Validacao;

public class ValidacaoData implements Validacao<String> {

	public static final String REGEX_DATA = "dd/MM/yyyy";
	public static final String DATA_INVALIDA = "Data inválida!";
	
	@Override
	public boolean valida(String validar) {
		return valida(validar, REGEX_DATA);
	}

	@Override
	public boolean valida(String validar, String regex) throws DateTimeParseException{
		try {
			LocalDate.parse(regex, DateTimeFormatter.ofPattern(regex));
			return true;
		} catch (DateTimeParseException dateTimeParseException) {
			return false;
		}
	}
}
