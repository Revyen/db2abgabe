package db2.abgabe2;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
	
	Session s;

	public static void main(String[] args) {
		Main m = new Main();
		m.dbConnect();
		m.datenEinfuegen();
		m.printKategorieArtikel();
		m.printKategorieArtikelNativ();
		
	}
	// Funktion zur erstellung der Verbindung zu der DB
	public void dbConnect()
	{
		// Session reset
		s = null;
		try
		{
			// Session wird aus der Konfiguration generiert und geöffnet
			SessionFactory sf = new Configuration().configure().buildSessionFactory();
			s = sf.openSession();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void dbDisconnect()
	{
		s.close();
	}

	public Session getSession()
	{
		return s;
	}

	// Hilfsfunktion zu Aufgabe 2
	public void artikelEinfuegen(String ArtikelName,Double Preis,lrds_ArtikelKategorie Kategorie,lrds_ArtikelKategorie UeberKategorie)
	{
		System.out.println("--------Artikel einfuegen--------");
		Transaction tx = s.beginTransaction();
		System.out.println("Insert Artikel");
		lrds_Artikel a = new lrds_Artikel();
		a.setName(ArtikelName);
		a.setPreis(Preis);
		a.setArtikelKategorie(Kategorie);
		a.setArtikelUeberKategorie(UeberKategorie);
		s.save(a);
		tx.commit();
	}
	
	// Hilfsfunktion zu Aufgabe 2
	public void kategorieEinfuegen(String KategorieName)
	{
		System.out.println("--------Kategorie einfuegen--------");
		
		Transaction tx = s.beginTransaction();
		System.out.println("Insert Kategorie");
		lrds_ArtikelKategorie a = new lrds_ArtikelKategorie();
		a.setName(KategorieName);
		s.save(a);
		tx.commit();
	}
	
	public lrds_ArtikelKategorie getKategorieFromDB(String Kategorie)
	{
		lrds_ArtikelKategorie a = new lrds_ArtikelKategorie();
		System.out.println("--------Kategrorie mit namen "+ Kategorie + "suchen--------");;
		String hql_query= "From lrds_ArtikelKategorie WHERE NAME = '"+Kategorie+"'";
		Query query = s.createQuery(hql_query);
		a = (lrds_ArtikelKategorie)query.getResultList().get(0);
		return a;
	}
	
	// Aufgabe 3
	public void printKategorieArtikel()
	{
		
		System.out.println("\n\n--------Liste Kategorien und Artikel auf--------");
		
		String hql_query= "from lrds_ArtikelKategorie";
		Long start = System.nanoTime();
		Query query = s.createQuery(hql_query);
		List<lrds_ArtikelKategorie> result = query.getResultList();
		Long qstop = System.nanoTime();
		for(lrds_ArtikelKategorie ak : result)
		{
			if(ak.getArtikel().size() != 0)
			{
				System.out.println("Kategorie: "+ak);
				System.out.println("----------------------------------------------------------------------------------------------");
				System.out.println("|Name            | ID    |      Preis | Kategorie                      | Überkategorie       |");
				System.out.println("----------------------------------------------------------------------------------------------");
				for(lrds_Artikel a : ak.getArtikel())
				{
					System.out.println(a);
				}
				System.out.println("--------------------------------------------------------------------------------------------- ");
			}
			
		}
		System.out.println();
		Long stop = System.nanoTime();
		System.out.printf("Query:%5d \nWhole:%5d\n",qstop-start,stop-start);
	}
	
	// Aufgabe 4
	public void printKategorieArtikelNativ()
	{
		
		System.out.println("\n\n--------Liste Kategorien und Artikel auf aber Nativ!--------");
		
		Long start = System.nanoTime();
		Query query = s.getNamedQuery("GetKategorien");
		List<lrds_ArtikelKategorie> result = query.getResultList();
		Long qstop = System.nanoTime();
		for(lrds_ArtikelKategorie ak : result)
		{
			if(ak.getArtikel().size() != 0)
			{
			System.out.println("Kategorie: "+ak);
			System.out.println("----------------------------------------------------------------------------------------------");
			System.out.println("|Name            | ID    |      Preis | Kategorie                      | Überkategorie       |");
			System.out.println("----------------------------------------------------------------------------------------------");
			for(lrds_Artikel a : ak.getArtikel())
			{
				System.out.println(a);
			}
			System.out.println("--------------------------------------------------------------------------------------------- ");
			}
		}
		System.out.println();
		Long stop = System.nanoTime();
		System.out.printf("Query:%5d \nWhole:%5d\n",qstop-start,stop-start);
	}

	// Aufgabe 2
	public void datenEinfuegen()
	{
		kategorieEinfuegen("Alkoholfreie");
		kategorieEinfuegen("Alkoholhaltige");
		kategorieEinfuegen("Milchhaltig");
		kategorieEinfuegen("lrds TheGoodStuff");
		kategorieEinfuegen("Saefte");
		kategorieEinfuegen("Wasser");
		kategorieEinfuegen("Limmonade");
		kategorieEinfuegen("Wein");
		kategorieEinfuegen("Bier");
		kategorieEinfuegen("Milchsorte");
		kategorieEinfuegen("lrds WasserMitKrassemGeschmack");
		s.clear();
		artikelEinfuegen("Multisaft", 2.95,getKategorieFromDB("Saefte"), getKategorieFromDB("Alkoholfreie"));
		artikelEinfuegen("Cola", 5.95,getKategorieFromDB("Limmonade"), getKategorieFromDB("Alkoholfreie"));
		artikelEinfuegen("Schwarzbier", 11.95,getKategorieFromDB("Bier"), getKategorieFromDB("Alkoholhaltige"));
		artikelEinfuegen("Krischsaft", 1.98,getKategorieFromDB("Saefte"), getKategorieFromDB("Alkoholfreie"));
		artikelEinfuegen("Vollmilch", 1.05,getKategorieFromDB("Milchsorte"), getKategorieFromDB("Milchhaltig"));
		artikelEinfuegen("Milchmix", 2.30,getKategorieFromDB("Milchsorte"), getKategorieFromDB("Milchhaltig"));
		artikelEinfuegen("GutWasser", 2.95,getKategorieFromDB("Wasser"), getKategorieFromDB("Alkoholfreie"));
		artikelEinfuegen("Altbier", 6.95,getKategorieFromDB("Bier"), getKategorieFromDB("Alkoholhaltige"));
		artikelEinfuegen("Pils", 4.50,getKategorieFromDB("Bier"), getKategorieFromDB("Alkoholhaltige"));
		artikelEinfuegen("Orangensprudel", 3.30,getKategorieFromDB("Limmonade"), getKategorieFromDB("Alkoholfreie"));
		artikelEinfuegen("Champagner", 133.33,getKategorieFromDB("Wein"), getKategorieFromDB("Alkoholhaltige"));
		artikelEinfuegen("Kakao", 1.50,getKategorieFromDB("Milchsorte"), getKategorieFromDB("Milchhaltig"));
		artikelEinfuegen("lrds Sangusi", 420.69,getKategorieFromDB("lrds WasserMitKrassemGeschmack"), getKategorieFromDB("lrds TheGoodStuff"));
		s.clear();
	}
}
