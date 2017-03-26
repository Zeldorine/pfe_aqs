package ets.pfe.aqs.modele;

/**
 * Approbation level to validate a new form revision.
 * There are 3 choices :
 * <ul>
 *      <li> No approbration, revision is valid with no approbation for all users
 *      <li> One approbation, revision need one approbation do be validate
 *      <li> Two approbation, revision need two approbation do be validate
 * </ul>
 * 
 * @author Zeldorine
 * @version 1.0.0
 */
public enum ApprobationType {
    REJECTED(-1),
    ZERO_APPROBATION(0),
    ONE_APPROBATION(1),
    TWO_APPROBATION(2);
    
    private int totalApprobation;
    
    /**
     * 
     * @param totalApprobation 
     */
    private ApprobationType(int totalApprobation){
        this.totalApprobation = totalApprobation;
    }
    
    /**
     * 
     * @return 
     */
    public int getTotalApprobation(){
        return totalApprobation;
    }
}
