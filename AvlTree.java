package AvlTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * AvlTree Class
 * @author Diego Hernández Delgado
 * @date 10/14/2019
 * @professor Fernando Esponda
 * @param <T> 
 */
public class AvlTree<T extends Comparable<T>>{

    //ATTRIBUTES::
    protected int cont;
    protected NodeAVL<T> root;
    
    /**
     * @method Constructor method
     */
    public AvlTree(){
        cont = 0;
        root=null;
    }
    
    /**
     * @param elem : the new element that you want to insert.
     */
    public void insert(T elem) {
        NodeAVL<T> actual, papa, nuevo;
        nuevo = new NodeAVL(elem);
        actual = root;
        papa = actual;
        boolean termine;
        if (root == null) {
            root = nuevo;
            cont++;
            return;
        }
        while (actual != null) {
            papa = actual;
            if (actual.getElem().compareTo(elem) > 0) {
                actual = actual.getLeft();
            } else {
                actual = actual.getRight();
            }
        }
        papa.cuelga(nuevo, papa);
        cont++;
        actual = nuevo;
        termine = false;
        while (actual != root && !termine) {
            if (actual.getFe() == 0 && actual.getRight() != null && actual.getLeft() != null) {
                actual = papa;
                papa = papa.getPapa();
                if (Math.abs(actual.getFe()) == 2) {
                    termine = true;
                }
            } else {
                if (papa.getRight() == actual) {
                    papa.setFe(papa.getFe() + 1);
                } else {
                    papa.setFe(papa.getFe() - 1);
                }
                actual = papa;
                papa = papa.getPapa();
                if (Math.abs(actual.getFe()) == 2) {
                    termine = true;
                }
            }
        }
        if (termine) 
            rotation(actual);  
    }
    
    /**
     * @param elem
     */
    public void remove(T elem){
        NodeAVL<T> temp=findNode(elem, root);
        NodeAVL<T> pap=null;
        
        if(temp!=null){
            cont--;
            
            if(temp==root){
                //Si existe el hijo derecho:
                if(temp.getRight()!=null){
                    NodeAVL<T> aux=temp.getRight();
                    NodeAVL<T> hi=root.getLeft();
                    NodeAVL<T> hd=root.getRight();
                    
                    while(aux.getLeft()!=null){
                        aux=aux.getLeft();
                    }
                    //Si no tiene hijos:
                    if(aux.getRight()==null && aux.getLeft()==null){
                        aux.getPapa().setIzq(null);
                        rotation(aux.getPapa());
                    }//Si existe el hijo derecho:
                    if(aux.getRight()!=null){
                        //Si el hijo derecho del auxiliar es menor al papá del auxiliar:
                        if(aux.getRight().getElem().compareTo(aux.getPapa().getElem())<=0){
                            aux.getPapa().setIzq(aux.getRight());
                            rotation(aux.getPapa());
                        }else{
                            //Si el hijo derecho del auxiliar es mayor al papá del auxiliar:
                            NodeAVL<T> paps=aux.getPapa();
                            NodeAVL<T> abue=paps.getPapa();
                            abue.cuelga(aux.getRight(), abue);
                            aux.getRight().cuelga(paps, aux.getRight());
                            rotation(abue);
                        }
                    }
                    root=aux;
                    root.cuelga(hi, root);
                    root.cuelga(hd, root);
                    temp.setIzq(null);
                    temp.setDer(null);
                    return;
                }
                //Si no hay hijo derecho:
                else{
                    if(temp.getLeft()!=null){
                        root=temp.getLeft();
                        rotation(root);
                    }
                    else
                        root=null;
                    temp.setIzq(null);
                    return;
                }
            }
            else{
                //el que buscamos no es la root
                pap=temp.getPapa();
                
                //Si no tiene hijos:
                if(temp.getRight()==null && temp.getLeft()==null){
                    if(pap.getLeft()==temp)
                        pap.setIzq(null);
                    else
                        pap.setDer(null);
                    temp.setPapa(null);
                    rotation(pap);
                }
                //Si existe hijo izquierdo, pero no hijo derecho:
                if(temp.getLeft()!=null && temp.getRight()==null){
                    pap.setIzq(temp.getLeft());
                    temp.setPapa(null);
                    temp.setIzq(null);
                    rotation(pap);
                }
                //Si no existe hijo izquierdo, pero sí existe el hijo derecho: 
                if(temp.getLeft()==null && temp.getRight()!=null){
                    //Si el hijo derecho es mayor al papá del temporal:
                    if(temp.getRight().getElem().compareTo(pap.getElem())<=0){
                        pap.getPapa().cuelga(temp.getRight(), pap.getPapa());
                        temp.getRight().cuelga(pap, temp.getRight());
                        rotation(temp.getRight());
                        temp.setPapa(null);
                        temp.setDer(null);
                        return;
                    }else{
                        //Si el hijo derecho es menor al papá del temporal:
                        pap.cuelga(temp.getRight(), pap);
                        temp.setPapa(null);
                        temp.setDer(null);
                        rotation(pap);
                        return;
                    }
                }
                
            }
        }
        else
            return;
        
    }
    
    /**
     * 
     * @param n
     * @return 
     */
    private NodeAVL<T> rotation(NodeAVL<T> n){
        //IZQ-IZQ
        if(n.getFe()==-2 && n.getLeft().getFe()<=0){
            
            NodeAVL<T> alfa=n;
            NodeAVL<T> papa=n.getPapa();
            NodeAVL<T> beta=alfa.getLeft();
            NodeAVL<T> gamma=beta.getLeft();
            NodeAVL<T> A=gamma.getLeft();
            NodeAVL<T> B=gamma.getRight();
            NodeAVL<T> C=beta.getRight();
            NodeAVL<T> D=alfa.getRight();
            
            gamma.cuelga(A, gamma);
            gamma.cuelga(B, gamma);
            alfa.cuelga(C, alfa);
            alfa.cuelga(D, alfa);
            beta.cuelga(alfa, beta);
            beta.cuelga(gamma, beta);
            if(papa!=null)
                papa.cuelga(beta, papa);
            else{
                beta.setPapa(null);
                root=beta;
            }
            alfa.setFe();
            gamma.setFe();
            beta.setFe();
            return beta;
        }
        //IZQ-DER
        if(n.getFe()==-2 && n.getLeft().getFe()>0){
            NodeAVL<T> alfa=n;
            NodeAVL<T> papa=n.getPapa();
            NodeAVL<T> beta=alfa.getLeft();
            NodeAVL<T> gamma=beta.getRight();
            NodeAVL<T> D= alfa.getRight();
            NodeAVL<T> A=beta.getLeft();
            NodeAVL<T> B=gamma.getLeft();
            NodeAVL<T> C=gamma.getRight();
            
            beta.cuelga(A, beta);
            beta.cuelga(B, beta);
            alfa.cuelga(C, alfa);
            alfa.cuelga(D, alfa);
            gamma.cuelga(beta, gamma);
            gamma.cuelga(alfa, gamma);
            
            if(papa!=null)
                papa.cuelga(gamma, papa);
            else{
                gamma.setPapa(null);
                root=gamma;
            }
            alfa.setFe();
            gamma.setFe();
            beta.setFe();
            return gamma;   
        }
        
        //DER-DER
        if(n.getFe()==2 && n.getRight().getFe()>=0){
            NodeAVL<T> alfa=n;
            NodeAVL<T> papa=n.getPapa();
            NodeAVL<T> beta=alfa.getRight();
            NodeAVL<T> gamma=beta.getRight();
            NodeAVL<T> A=alfa.getLeft();
            NodeAVL<T> B=beta.getLeft();
            NodeAVL<T> C=gamma.getLeft();
            NodeAVL<T> D=gamma.getRight();
            
            if(A!=null){
                alfa.cuelga(A, alfa);
            }
            if(B!=null){
                alfa.cuelga(B, alfa);
            }
            else{
                beta.setPapa(null);
            }
            if(C!=null){
                gamma.cuelga(C, gamma);
            }
            if(D!=null){
                gamma.cuelga(D, gamma);
            }
            beta.cuelga(alfa, beta);
            beta.cuelga(gamma, beta);
            
            if(papa!=null)
                papa.cuelga(beta, papa);
            else{
                beta.setPapa(null);
                root=beta;
            }
            alfa.setFe();
            gamma.setFe();
            beta.setFe();
            return beta;   
        }
         //DER-IZQ
        if(n.getFe()==2 && n.getRight().getFe()<0){
            NodeAVL<T> alfa=n;
            NodeAVL<T> papa=n.getPapa();
            NodeAVL<T> beta=alfa.getRight();
            NodeAVL<T> gamma=beta.getLeft();
            NodeAVL<T> A=alfa.getLeft();
            NodeAVL<T> B=gamma.getLeft();
            NodeAVL<T> C=gamma.getRight();
            NodeAVL<T> D=beta.getRight();
            
            alfa.cuelga(A, alfa);
            alfa.cuelga(B, alfa);
            beta.cuelga(C, beta);
            beta.cuelga(D, beta);
            gamma.cuelga(alfa, gamma);
            gamma.cuelga(beta, gamma);
            
            if(papa!=null)
                papa.cuelga(gamma, papa);
            else{
                gamma.setPapa(null);
                root=gamma;
            }
            alfa.setFe();
            gamma.setFe();
            beta.setFe();
            return gamma;   
              
        }
        else
            return null;
    }
    
    /**
     * @param elem
     * @return 
     */
    public boolean find(T elem) {
        if (findNode(elem, root) == null) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @param elem
     * @param actual
     * @return 
     */
    private NodeAVL findNode(T elem, NodeAVL actual) {
        NodeAVL<T> temp;
        if (actual == null) {
            return null;
        }
        if (actual.getElem().equals(elem)) {
            return actual;
        }
        temp = findNode(elem, actual.getLeft());
        if (temp == null) {
            temp = findNode(elem, actual.getRight());
        }
        return temp;
    }

    /**
     * 
     */
    public void print() {
        ArrayList<NodeAVL<T>> aux = new ArrayList<>();
        ArrayList<T> lista = new ArrayList<>();
        ArrayList<Integer> lista2 = new ArrayList<>();
        aux.add(root);

        NodeAVL<T> temp;
        while (!aux.isEmpty()) {
            temp = aux.remove(0);
            lista.add(temp.getElem());
            lista2.add(temp.getFe());
            if (temp.getLeft() != null) {
                aux.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                aux.add(temp.getRight());
            }
        }
        while (lista.iterator().hasNext()) {
            T x = lista.remove(0);
            int y = lista2.remove(0);
            System.out.print(" " + x + " (" + y + ")\n");
        }
    }
     
    /**
     * Main method for testing
     * @param args 
     */
    public static void main(String [] args){
        AvlTree<Character> arbol = new AvlTree();
        arbol.insert('c');
        arbol.insert('e');
        arbol.insert('b');
        arbol.insert('z');
        arbol.print();
        
        System.out.println("\n");
        arbol.remove('b');
        arbol.print();
    }
}