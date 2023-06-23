package lmv.planejamentofinanceiro.validacao;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lmv.planejamentofinanceiro.interfaces.Validacao;

public class ValidacaoData implements Validacao<String> {

	public static final String REGEX_DATA_COMPLETA = "dd/MM/yyyy",
											REGEX_DATA_DIA_MES = "dd/MM",
											DATA_INVALIDA = "Data inválida!";
	
	@Override
	public boolean valida(String validar) {
		return valida(validar, REGEX_DATA_COMPLETA);
	}

	@Override
	public boolean valida(String validar, String regex) {
		if (regex.equalsIgnoreCase(REGEX_DATA_COMPLETA)) 
			return validaData(validar, regex);
			
		if (regex.equalsIgnoreCase(REGEX_DATA_DIA_MES))
			return validaDiaMes(validar, regex);
		
		return false;
	}
	
	private boolean validaDiaMes(String validar, String regex) {
		try {
			MonthDay.parse(validar, DateTimeFormatter.ofPattern(regex));
			return true;
		} catch (DateTimeParseException dateTimeParseException) {
			return false;
		}
	}

	private boolean validaData(String validar, String regex) {
		try {
			LocalDate.parse(validar, DateTimeFormatter.ofPattern(regex));
			return true;
		} catch (DateTimeParseException dateTimeParseException) {
			return false;
		}
	}

	public static LocalDate formatarData(String data, String regex) throws DateTimeParseException {
		LocalDate localDate;
		
		try {
			localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern(regex));
			return localDate;
		} catch (DateTimeParseException dateTimeParseException) {
			throw dateTimeParseException;
		}
	}
	
	public static MonthDay formatarMesAno(String data, String regex) throws DateTimeParseException {
		MonthDay monthDay;
		
		try {
			monthDay = MonthDay.parse(data, DateTimeFormatter.ofPattern(regex));
			return monthDay;
		} catch (DateTimeParseException dateTimeParseException) {
			throw dateTimeParseException;
		}
	}
}
