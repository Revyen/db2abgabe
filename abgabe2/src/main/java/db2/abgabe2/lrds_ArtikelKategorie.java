package db2.abgabe2;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.ColumnResult;

@Entity
@SqlResultSetMapping(name= "MappingKategorie",
entities=@EntityResult(entityClass=db2.abgabe2.lrds_ArtikelKategorie.class,
fields = {
		@FieldResult(name="akName", column="NAME"),
		@FieldResult(name="akID", column="AK_ID")
}))
@NamedNativeQuery(name="GetKategorien",
query="Select * from lrds_Artikelkategorie",resultSetMapping="MappingKategorie")


public class lrds_ArtikelKategorie {
	@Column(name="NAME",length = 50)
	private String akName;
	@Id
	@Column(name="AK_ID",nullable = false,updatable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "id_Sequence_Kategorie")
	private Long akID;
	@OneToMany(mappedBy = "lrds_artikelkategorie")
	private List<lrds_Artikel> lrds_artikelkategorie;
	
	public lrds_ArtikelKategorie() {}
	
	public void setName(String name)
	{
		this.akName = name;
	}
	
	public List<lrds_Artikel> getArtikel()
	{
		return lrds_artikelkategorie;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.akName;
	}
	
}
