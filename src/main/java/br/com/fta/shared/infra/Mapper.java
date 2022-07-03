package br.com.fta.shared.infra;

public interface Mapper<S, T> {

	T map(S source);
}
