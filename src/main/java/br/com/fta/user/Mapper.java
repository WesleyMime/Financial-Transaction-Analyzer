package br.com.fta.user;

public interface Mapper<S, T> {

	T map(S source);
}
