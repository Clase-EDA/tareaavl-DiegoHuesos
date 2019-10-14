package AvlTree;
/**
 * NodeAVL Class
 * @author Diego Hernández Delgado
 * @date 10/14/2019
 * @professor Fernando Esponda
 * @param <T> 
 */
public class NodeAVL<T extends Comparable>{  
    
    //ATTRIBUTES:
    protected NodeAVL<T> izq,der,papa;
    protected int fe;
    protected T elem;

    /**Constructor method
     * @param element: the data that you want to save in the node.
     */
    public NodeAVL(T element) {
        elem=element;
        fe=0;
        izq=null;
        der=null;
        papa=null;
    }
    
    /**
     * Cuelga method
     * @param nodo
     * @param otroyo 
     */
    public void cuelga(NodeAVL<T> nodo, NodeAVL<T> otroyo){
        if(nodo==null)
            return;
        if(elem.compareTo(nodo.getElem()) > 0)
            izq=nodo;
        else
            der=nodo;
        nodo.setPapa(otroyo);
        
    }
    
    public T getElem() {
        return elem;
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public NodeAVL<T> getLeft() {
        return izq;
    }

    public void setIzq(NodeAVL<T> izq) {
        this.izq = izq;
    }

    public NodeAVL<T> getRight() {
        return der;
    }

    public void setDer(NodeAVL<T> der) {
        this.der = der;
    }

    public NodeAVL<T> getPapa() {
        return papa;
    }

    public void setPapa(NodeAVL<T> papa) {
        this.papa = papa;
    }

    public int getFe() {
        return fe;
    }

    public void setFe() {
        fe= getHeight(der)-getHeight(izq);
    }
    
    /**
     * 
     * @param actual
     * @return 
     */
    public int getHeight(NodeAVL<T> actual){
        if(actual == null)
            return 0;
        return Math.max(numDescendientesIzq(), numDescendientesDer());
    }
    
    public int numDescendientes(){
        int resp=0;
        if(izq!=null)
            resp=izq.numDescendientes()+1;
        if(der!=null)
            resp=der.numDescendientes()+1;
        return resp;
    }
    
    public int numDescendientesIzq(){
        int resp=0;
        if(izq!=null)
            resp=izq.numDescendientesIzq()+1;
        return resp;
    }
    
    public int numDescendientesDer(){
        int resp=0;
        if(der!=null)
            resp=der.numDescendientesDer()+1;
        return resp;
    }
    

    public void setFe(int fe) {
        this.fe = fe;
    }
    
    @Override
    public String toString(){
        return "Elemento: " + elem.toString() + "Factor de Equilibrio: " + fe;
    }    
}
