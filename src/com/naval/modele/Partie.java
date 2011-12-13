package com.naval.modele;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import com.naval.ordres.Ordre;
import com.naval.outils.Config;
import com.naval.outils.Distance;
import com.naval.report.Report;

public class Partie implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6793761344976479155L;
    public Coordonnees coords;
    public List<Navire> navires;
    public List<Joueur> joueurs;
    public int minute = 0;
    public String nom;
    public List<String> messages = new ArrayList<String>();
    public int visibilite;
    int heure;
    int minuteReelle;
    public List<Ordre> ordres;

    Partie() {
        navires = new ArrayList<Navire>();
        joueurs = new ArrayList<Joueur>();
    }

    public static Partie creer(Reader reader) throws IOException {

        Partie partie = new Partie();

        BufferedReader br = new BufferedReader(reader);

        // Nom de la partie
        partie.nom = br.readLine();

        // visibilite 
        partie.visibilite = Integer.parseInt(getLine(br));

        // Heure et minute
        partie.heure = Integer.parseInt(getLine(br));
        partie.minuteReelle = Integer.parseInt(getLine(br));

        // lecture des joueurs
        int nbJoueurs = Integer.parseInt(getLine(br));
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur j = new Joueur(br.readLine());
            partie.joueurs.add(j);
        }

        // lecture des navires		
        int nbNavires = Integer.parseInt(getLine(br));

        partie.coords = new Coordonnees(nbNavires);

        for (int i = 0; i < nbNavires; i++) {
            String navireDescription = br.readLine();
            Navire navire = new Navire(navireDescription);
            partie.navires.add(navire);
        }

        partie.ordres = new ArrayList<Ordre>();
        return partie;
    }

    /**
     * Permet de sauter les lignes de commentaires
     * @param br
     * @return
     * @throws IOException
     */
    private static String getLine(BufferedReader br) throws IOException {
        String line = br.readLine();

        if (line.indexOf("#") != -1) {
            return br.readLine();
        }

        return line;
    }

    public void executerTour() throws IOException {
        prendreEnCompteLesOrdres();

        // TODO : revoir car les ordres de mvt s'activent à la fin du tour.

        for (Navire n : navires) {
            n.bouge();
            coords.ajouter(minute, n);
        }

        minute++;

        if ((minuteReelle + 1) > 60) {
            heure++;
            minuteReelle = 60 - minuteReelle + 1;
        } else {
            minuteReelle++;
        }

        estimerDistance();

        ordres.clear();

        for (Navire n : navires) {
            // creation des 3 ordres pour le tour courant.
            ordres.add(new Ordre(n.id, minute));
            ordres.add(new Ordre(n.id, minute));
            ordres.add(new Ordre(n.id, minute));
        }
    }

    private void estimerDistance() {
        for (Navire n : navires) {
            for (Navire p : navires) {
                if (n.id != p.id) {
                    int distance = (int) Distance.calculer(n.x, n.y, p.x, p.y);
                    System.out.println(n.nom + "->" + p.nom + "=" + distance + " yards");
                }
            }
        }

    }

    private void prendreEnCompteLesOrdres() throws FileNotFoundException,
            IOException {
        // lectures des ordres
        //FileReader fr = new FileReader(nom + ".txt");

        //List<Ordre> ordres = Ordre.lire(fr);
        if (ordres.size() == 0) {
            return;
        }

        for (Ordre o : ordres) {
            Navire n = navires.get(o.idNavire);

            // 2.3.2.2 Giration 
            if (o.modificationCap != 0) {
                Report.add(n.nom + " modification de cap:" + (n.cap + o.modificationCap));
                n.cap += o.modificationCap;
            }
            // 2.3.1.3 Accélération et décélération
            if (o.acceleration != 0) {
                Report.add(n.nom + " modification de vitesse:" + (n.vitesse + o.acceleration));
                n.vitesse += o.acceleration;
            }
        }
    }

    public void report(Writer writer) throws IOException {
        for (int m = 0; m < minute; m++) {
            Donnees donnees = coords.donneesPour(m);
            for (Navire n : navires) {
                StringBuilder sb = new StringBuilder();
                sb.append(donnees.minute);
                sb.append(": id=[").append(n.id);
                sb.append("] nom=[").append(donnees.noms[n.id]);
                sb.append("] X=[").append(donnees.xs[n.id]);
                sb.append("] Y=[").append(donnees.ys[n.id]).append("]");

                writer.write(sb.toString() + "\r\n");
            }
        }
    }

    public Navire getNavire(int n) {
        // TODO Auto-generated method stub
        return navires.get(n);
    }

    public static Partie load(String nom) throws IOException, ClassNotFoundException {
        Partie partie = null;

        FileInputStream fis = new FileInputStream(nom);
        // création d'un "flux objet" avec le flux fichier
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
            // désérialisation : lecture de l'objet depuis le flux d'entr�e
            partie = (Partie) ois.readObject();
        } finally {
            // on ferme les flux
            try {
                ois.close();
            } finally {
                fis.close();
            }
        }

        Report.set(partie);

        if (partie.ordres == null) {
            partie.ordres = new ArrayList<Ordre>();
        }

        return partie;
    }

    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream(Config.getRepTravail() + nom + ".serial");

        ObjectOutputStream oos = new ObjectOutputStream(fos);

        try {
            // sérialisation : écriture de l'objet dans le flux de sortie
            oos.writeObject(this);
            // on vide le tampon
            oos.flush();
        } finally {
            //fermeture des flux
            try {
                oos.close();
            } finally {
                fos.close();
            }
        }
    }

    public String getHeureMinute() {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb);

        formatter.format("%1$d:%2$02d", heure, minuteReelle);

        return sb.toString();
    }
}
