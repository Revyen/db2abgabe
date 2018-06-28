package db2.abgabe2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
public class lrds_Artikel {
	@Column(name="NAME",length = 50)
	private String artikelName;
	@Id
	@Column(name="A_ID",nullable = false,updatable = false)
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "id_Sequence_Artikel")
	private Long artikelNummer;
	private Double preis;
	@ManyToOne
	@JoinColumn(name="akID", referencedColumnName="AK_ID")
	private lrds_ArtikelKategorie lrds_artikelkategorie;
	
	@ManyToOne
	@JoinColumn(name="akUeID", referencedColumnName="AK_ID")
	private lrds_ArtikelKategorie lrds_artikelUeberkategorie;
	
	public lrds_Artikel() {}
	
	public void setName(String name)
	{
		this.artikelName = name;
	}
	
	public void setArtikelKategorie(lrds_ArtikelKategorie kategorie)
	{
		this.lrds_artikelkategorie = kategorie;
	}
	
	public void setArtikelUeberKategorie(lrds_ArtikelKategorie uekategorie)
	{
		this.lrds_artikelUeberkategorie = uekategorie;
	}
	
	public void setPreis(Double preis)
	{
		this.preis = preis;
	}
	
	public lrds_ArtikelKategorie getKategorie()
	{
		return this.lrds_artikelkategorie;
	}
	
	public lrds_ArtikelKategorie getUeberKategorie()
	{
		return this.lrds_artikelUeberkategorie;
	}
	
	@Override
	public String toString() {
		return String.format("|%-15s | %-5d | %9.2f€ | %-30s | %-20s|", this.artikelName,this.artikelNummer,this.preis,this.lrds_artikelkategorie,this.lrds_artikelUeberkategorie).toString();
	}
}
