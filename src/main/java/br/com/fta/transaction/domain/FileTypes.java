package br.com.fta.transaction.domain;

import java.io.InputStream;
import java.util.List;

import br.com.fta.shared.infra.Mapper;

public enum FileTypes {

	CSV {
		@Override
		public Mapper<InputStream, List<Transaction>> mapper() {
			return new TransactionMapperCSV();
		}
	},
	XML {
		@Override
		public Mapper<InputStream, List<Transaction>> mapper() {
			return new TransactionMapperXML();
		}
	};

	public abstract Mapper<InputStream, List<Transaction>> mapper();
	
}
