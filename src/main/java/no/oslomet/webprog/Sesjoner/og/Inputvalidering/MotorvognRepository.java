package no.oslomet.webprog.Sesjoner.og.Inputvalidering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorvognRepository {

    @Autowired
    private JdbcTemplate db;

    private Logger logger = LoggerFactory.getLogger(MotorvognRepository.class);

    public boolean lagreMotorvogn(Motorvogn motorvogn){
        //Since we are now storing only the type, we are not sending in the merke
        //You don't need to do it like this; instead, you can store both merke and type. This is just a demonstration
        String sql = "INSERT INTO Motorvogn(personnr, navn, adresse, kjennetegn, type) VALUES (?, ?, ?, ?, ?)";
        try {
            db.update(sql, motorvogn.getPersonnr(), motorvogn.getNavn(), motorvogn.getAdresse(), motorvogn.getKjennetegn(), motorvogn.getType());
            return true;
        }
        catch (Exception e) {
            logger.error("Kunne ikke lagre motorvogn " + e);
            return false;
        }
    }

    public boolean endreMotorvogn(Motorvogn motorvogn){
        String sql = "UPDATE Motorvogn SET personnr=?, navn=?, adresse=?, kjennetegn=?, type=? WHERE id=?";
        try {
            db.update(sql,   motorvogn.getPersonnr(), motorvogn.getNavn(), motorvogn.getAdresse(),
                    motorvogn.getKjennetegn(), motorvogn.getType(), motorvogn.getId());
            return true;
        }
        catch (Exception e) {
            logger.error("Kunne ikke endre motorvogn " + e);
            return false;
        }
    }

    public List<Motorvogn> hentAlle(){
        //Since not all data is available in Motorvogn, we need to join the tables and get the missing data from Biler
        String sql = "SELECT m.id, m.personnr, m.navn, m.adresse, m.kjennetegn, b.merke, b.type " +
                "FROM Motorvogn as m INNER JOIN Biler as b " +
                "ON m.type = b.type";
        try {
            List<Motorvogn> motorvognList = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class));
            return motorvognList;
        }
        catch (Exception e) {
            logger.error("Kunne ikke hente motorvogner " + e);
            return null;
        }
    }

    public Motorvogn hentEnMotorvogn(int id) {
        //Since not all data is available in Motorvogn, we need to join the tables and get the missing data from Biler
        String sql = "SELECT m.id, m.personnr, m.navn, m.adresse, m.kjennetegn, b.merke, b.type " +
                "FROM Motorvogn as m INNER JOIN Biler as b " +
                "ON m.type = b.type AND m.id=?";
        try {
            List<Motorvogn> motorvognList = db.query(sql, new BeanPropertyRowMapper(Motorvogn.class), id);
            return motorvognList.get(0);
        }
        catch (Exception e) {
            logger.error("Kunne ikke finne motorvogn " + e);
            return null;
        }
    }

    public List<Bil> hentBiler(){
        String sql = "SELECT * FROM Biler;";
        try {
            List<Bil> bilList = db.query(sql, new BeanPropertyRowMapper(Bil.class));
            return bilList;
        }
        catch (Exception e) {
            logger.error("Kunne ikke hente biler " + e);
            return null;
        }
    }

    public boolean slettAlle(){
        String sql = "DELETE FROM Motorvogn;";
        try {
            db.update(sql);
            return true;
        }
        catch (Exception e) {
            logger.error("Kunne ikke slette motorvogner " + e);
            return false;
        }
    }

    public boolean slettEn(int id){
        String sql = "DELETE FROM Motorvogn WHERE id=?;";
        try {
            db.update(sql, id);
            return true;
        }
        catch (Exception e) {
            logger.error("Kunne ikke slette motorvogner " + e);
            return false;
        }
    }

    public boolean[] login(Kunde kunde) {
        String SQL = "SELECT * FROM Kunde WHERE brukernavn = ?";

        boolean[] list = {false, false};
        Kunde dbKunde = db.queryForObject(SQL, new BeanPropertyRowMapper<>(Kunde.class), kunde.getBrukernavn());
        if (dbKunde != null) {
            if (dbKunde.getPassord().equals(kunde.getPassord())) {
                list[0] = true;
                if (dbKunde.getRettighet().equals("admin")) {
                    list[1] = true;
                }
            }
        }
        return list;
    }

    public boolean registrerBruker(Kunde kunde) {
        String SQL = "INSERT INTO Kunde(brukernavn, passord, rettighet) VALUES(?,?,?)";
        try {
            db.update(SQL, kunde.getBrukernavn(), kunde.getPassord(), kunde.getRettighet());
            return true;
        } catch (Exception e) {
            logger.error("Kunne ikke lage kunde");
            return false;
        }
    }
}
