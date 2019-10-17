/**
 * Tarea 2.- Programación de la clase Árbol AVL
 * @author: Diego Hernández Delgado
 * Clave única: 176262
 * Materia: Estructuras de datos avanzadas
 * Profesor: Fernando Esponda
 * Fecha de entrega: 15/10/2019
 * 
 * "NodeAVL Class"
 */

package avltree;

public class NodeAVL <T extends Comparable<T>>{ 
    //ATRIBUTOS:
    protected NodeAVL<T> left, right; 
    protected int  fe, height; 
    private T value;
	
    //CONSTRUCTORES:
    NodeAVL(){
    }
    NodeAVL(T value){ 
        this();
        this.value = value; 
        height = 1; 
    } 
       
    //MÉTODO PARA ACTUALIZAR LA ALTURA DEL NODO:
    public void updateHeight(int newHeight){
        this.height=newHeight;
    }
    //MÉTODO PARA ACTUALIZAR EL FACTOR DE EQUILIBRIO:
    public void updateFe() {
        this.fe= this.right.getHeight() - this.left.getHeight();
    }
               
    //MÉTODO PARA OBTENER EL NÚMERO DE HIJOS DEL SUB-ÁRBOL IZQUIERDO:
    public int numLeftSons(){
        int resp=0;
        if(this.left!=null)
            resp=this.left.numLeftSons()+1;
        return resp;
    }

    //MÉTODO PARA OBTENER EL NÚMERO DE HIJOS DEL SUB-ÁRBOL DERECHO:
    public int numRightSons(){
        int resp=0;
        if(this.right!=null)
            resp=this.right.numRightSons()+1;
        return resp;
    }     
        
    //GETTERS:
    public NodeAVL<T> getLeft(){
        return this.left;
    }    
    public NodeAVL<T> getRight(){
        return this.right;
    }
    public int getFe(){
        return this.fe;
    }
    public T getValue(){
        return this.value;
    }
    public int getHeight(){
        return this.height;
    }

        
    //SETTERS:
    public void setLeft(NodeAVL<T> newLeft){
        this.left = newLeft;
    }
    public void setRight(NodeAVL<T> newRight){
        this.right = newRight;
    }
    public void setValue(T newValue){
        this.value = newValue;
    }
    

    //TO_STRING:
    @Override
    public String toString(){
        return this.value.toString();
    }
        
    //EQUALS:
    public boolean equals(NodeAVL<T> other){
        boolean res = false;
        if(this.value.compareTo( other.getValue() ) == 0)
            res = true;
        return res;
    }
        
    //COMPARE_TO:
    public int compareTo(NodeAVL<T> other){
        int res = 0;
        if(this.value.compareTo( other.getValue() ) > 0)
            res = 1;
        else 
            if(this.value.compareTo( other.getValue() ) < 0)
                res = -1;
        return res;
    }
} 