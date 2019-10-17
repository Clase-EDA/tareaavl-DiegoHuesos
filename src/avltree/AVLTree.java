/**
 * Tarea 2.- Programación de la clase Árbol AVL
 * @author: Diego Hernández Delgado
 * Clave única: 176262
 * Materia: Estructuras de datos avanzadas
 * Profesor: Fernando Esponda
 * Fecha de entrega: 15/10/2019
 * 
 * "AVLTree Class"
 */

package avltree;

public class AVLTree <T extends Comparable<T>> { 
    //ATRIBUTOS:
    NodeAVL<T> root; 
    public int cont;
    
    //CONSTRUCTOR:
    public AVLTree(){
        root = null;
        cont = 0;
    }
        
    //FUNCIÓN PARA OBTENER LA ALTURA DEL ÁRBOL O SUB-ÁRBOL:   
    public int height(NodeAVL<T> node) { 
	if (node == null) 
            return 0; 
        else
            return node.getHeight(); 
    } 

    //FUNCIÓN PARA OBTENER EL MÁXIMO ENTRE DOS ENTEROS:
    private int max(int a, int b) { 
	return (a > b) ? a : b;   //Profe, nótese el uso de esta sintáxis súper pro. ;)
    } 

    //FUNCIÓN PARA ROTAR A LA DERECHA EL SUB-ÁRBOL 
    //YA SEA POR UN CASO IZQ-IZQ O IZQ-DER:
    public NodeAVL<T> rightRotate(NodeAVL<T> a){ 
        //VARIABLES AUXILIARES:
	NodeAVL<T> b = a.left; 
	NodeAVL<T> subRight = b.right; 

	//ROTAR:
	b.right = a; 
	a.left = subRight; 

	//ACTUALIZAR ALTURAS:
	a.updateHeight( max(height(a.left), height(a.right)) + 1  ); 
	b.updateHeight( max(height(b.left), height(b.right)) + 1  );

	//REGRESAR LA NUEVA RAÍZ:
	return b; 
    } 

    //FUNCIÓN PARA ROTAR A LA IZQUIERDA EL SUB-ÁRBOL 
    //YA SEA POR UN CASO DER-DER O DER-IZQ:
    public NodeAVL leftRotate(NodeAVL<T> a) { 
        //VARIABLES AUXILIARES:
	NodeAVL b = a.right; 
	NodeAVL subLeft = b.left; 

	//ROTAR:
	b.left = a; 
	a.right = subLeft; 

	//ACTUALIZAR ALTURAS:
	a.updateHeight( max(height(a.left), height(a.right)) + 1 ); 
	b.updateHeight( max(height(b.left), height(b.right)) + 1 ); 

	//REGRESR LA NUEVA RAÍZ:
	return b; 
    } 

    //FUNCIÓN PARA OBTENER EL FACTOR DE EQUILIBRIO:
    public int getFe(NodeAVL<T> node) { 
	if (node == null) 
            return 0; 
        else
            return height(node.right) - height(node.left); 
    } 

    //FUNCIÓN RECURSIVA PARA INSERTAR DATO::
    public void insert(T value){
        root = insert(root, value);
    }
    private NodeAVL insert(NodeAVL<T> node, T value) { 
        
	// 1.INSERTAR EL NODO EN SU LUGAR IN-ORDEN:
	if (node == null){
            cont++;
            return (new NodeAVL<>(value)); 
        }
        // NO SE ACEPTAN ELEMENTOS REPETIDOS, SI SE REQUIERE, 
        //HAY QUE AGREGARLE UN IGUAL A ALGUNA CONDICIÓN:
	if (value.compareTo(node.getValue()) < 0) 
            node.left = insert(node.left, value); 
	else if (value.compareTo(node.getValue()) > 0) 
		node.right = insert(node.right, value); 
	else 
            return node; 

	// 2. ACTUALIZAR LA ALTURA DEL NODO (PAPÁ)
	node.height = 1 + max(height(node.left), height(node.right)); 

	// 3. OBTENER EL FACTOR DE EQUILIBRIO DEL NODO (PAPÁ)Get the balance factor of this ancestor 
	// PARA CHECAR SI ESTÁ DESEQUILIBRADO Y HACIA QUE LADO O QUE CASO ES: 
	int balance = getFe(node); 

        //HAY 4 CASOS DIFERENTES DE DESEQUILIBRIO:
        
        //CASO IZQUIERDA - IZQUIERDA:
	if (balance < -1  &&  value.compareTo(node.left.getValue()) < 0) 
            return rightRotate(node);
        
        //CASO IZQUIERDA - DERECHA: 
	if (balance < -1 && value.compareTo(node.left.getValue()) > 0) { 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
	} 
        
	//CASO DERECHA - DERECHA: 
	if (balance > 1 && value.compareTo(node.right.getValue()) > 0) 
            return leftRotate(node); 
        
	//CASO DERECHA - IZQUIERDA:  
	if (balance > 1 && value.compareTo(node.right.getValue()) < 0) { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
	} 

	//REGRESA EL NODO (QUE NO CAMBIÓ) 
	return node; 
    } 

    
    //MÉTODO (AUXILIAR) PARA OBTENER EL NODO CON EL VALOR MÍNIMO DEL ÁRBOL:
    public NodeAVL<T> minValueNode(NodeAVL<T> node){  
        NodeAVL<T> min = node;  
  
        /* loop down to find the leftmost leaf */
        while (min.left != null)  
            min = min.left;  
  
        return min;  
    }  
       
    //MÉTODO RECURSIVO PARA ELIMINAR UN NODO DADO:
    public void deleteNode(T value){
        deleteNode(root, value);
    }
    public NodeAVL<T> deleteNode(NodeAVL<T> node, T value){  
    //1.- SI EL ÁRBOL ESTÁ VACÍO:
    if (node == null)
        return node;  
  
    //SI EL VALOR BUSCADO ES MENOR QUE EL VALOR 
    //DEL NODO SE VA HACIA EL SUB-ÁRBOL IZQUIERDO: 
    if (value.compareTo(node.getValue()) < 0)  
        node.left = deleteNode(node.left, value);  
  
    //SI EL VALOR BUSCADO ES MAYOR QUE EL VALOR 
    //DEL NODO SE VA HACIA EL SUB-ÁRBOL DERECHO:  
    else if (value.compareTo(node.getValue()) > 0)  
        node.right = deleteNode(node.right, value);  
    
        //SI EL VALOR BUSCADO ES EL MISMO QUE EL NODO ACTUAL,
        //ENTOCNES ES EL NODO A ELIMINAR:
        else{  
            // NODO CON SÓLO UN HIJO O SIN HIJOS:
            if ((node.left == null) || (node.right == null))  
            {  
                NodeAVL<T> temp = null;  
                if (temp == node.left)  
                    temp = node.right;  
                else
                    temp = node.left;  
  
                //SIN HIJOS: 
                if (temp == null){  
                    temp = node;  
                    node = null;  
                }
                //CON UN SOLO HIJO
                else 
                    node = temp; //GUARDA AL HIJO TEMPORALMENTE AL HIJO PARA NO PERDERLO
                
                //DISMINUYE LA CANTIDAD DE ELEMNTOS:
                cont--; 
            }  
            //NODO CON DOS HIJOS:
            else{  
                //GUARDA TEMPORALMENTE AL SUCESOR INORDEN
                //QUE ES EL NODO MÁS PEQUEÑO DEL SUB-ÁRBOL DERECHO:
                NodeAVL<T> temp = minValueNode(node.right);  
  
                //COPIA EL VALOR DEL SUCESOR INORDEN EN ESTE NODO:
                node.setValue(temp.getValue());  
  
                //BORRA AL SUCESOR INORDEN:
                node.right = deleteNode(node.right, temp.getValue());  
                
            }  
            
        }  
  
        //SI EL ÁRBOL SÓLO TENÍA UN NODO Y ÉSTE FUE BORRADO:
        if (node == null)  
            return node;  
  
        //2.-SE ACTUALIZA LA ALTURA DEL NODO 
        node.height = max(height(node.left), height(node.right)) + 1;  
  
        //3.-SE OBTIENE EL FACTOR DE EQUILIBRIO DEL NODO Y VERIFICAR
        //HACIA QUE LADO SE DESEQUILIBRÓ:
        int balance = getFe(node);  
  
        //4 CASOS:
        
        //CASO IZQUIERDA - IZQUIERDA:  
        if (balance < -1 && getFe(node.left) < 0)  
            return rightRotate(node);  
  
        //CASO IZQUIERDA - DERECHA: 
        if (balance < -1 && getFe(node.left) >= 0)  {  
            node.left = leftRotate(node.left);  
            return rightRotate(node);  
        }  
  
        //CASO DERECHA - DERECHA:  
        if (balance > 1 && getFe(node.right) > 0)  
            return leftRotate(node);  
  
        //CASO DERECHA - IZQUIERDA:  
        if (balance > 1 && getFe(node.right) <= 0)  {  
            node.right = rightRotate(node.right);  
            return leftRotate(node);  
        }  
        
        //REGRESA EL NODO:
        return node;  
    }
        

    //MÉTODO PARA IMPRIMIR EL ÁRBOL EN PRE-ORDEN (DE IZQUIERDA A DERECHA):
    public void printPreOrder(NodeAVL<T> nodeAVL) { 
	if (nodeAVL != null) { 
            System.out.print(nodeAVL.getValue() + "(" + this.getFe(nodeAVL)*(1) + ")  "); 
            printPreOrder(nodeAVL.left); 
            printPreOrder(nodeAVL.right); 
	} 
    } 
             
    //MÉTODO PARA BUSCAR UN NODO QUE CONTENGA UN VALOR DADO: 
    public String find(T value) {
        NodeAVL<T> nodeSearched = findNode(root, value);
        if(nodeSearched == null)
            return "No se encontró el: " + value.toString();
        else
            return "Sí se encontró el: "+value.toString();
    }
    private NodeAVL<T> findNode(NodeAVL<T> node, T value){
        if(node == null)
            return null;
        else
            if( node.getValue().equals(value))
                return node;
            else
                if(value.compareTo(node.getValue()) < 0)
                    return findNode(node.getLeft(), value);
                else
                    return findNode(node.getRight(), value);
    }
    
    //GETTERS:
    public int getCont(){
        return this.cont;
    }
    public NodeAVL<T> getRoot(){
        return this.root;
    }
    
    //MAIN CLASS PARA REALIZAR LAS PRUEBAS:
    public static void main(String[] args) { 
	AVLTree<Integer> tree = new AVLTree<>(); 
	tree.insert(100); 
	tree.insert(200); 
	tree.insert(300); 
	tree.insert(400); 
	tree.insert(500); 
	tree.insert(250); 
        
	/*
		300 
		/ \ 
              200  400 
              / \    \	
            100 250  500 
        */

	tree.printPreOrder(tree.root); 
        
        System.out.println("\n\nCantidad de nodos: " + tree.cont );
        System.out.println( "Altura del árbol: " + tree.root.getHeight()+ "\n");

        //BORRAR UN NODO:
        tree.deleteNode( 400);
        
        
        tree.printPreOrder(tree.root);
        
        /* 
		300(-1) 
		/    \ 
             200(0)   500(0) 
            /     \    	
          100(0)   250(0)   
        
        */
        
        System.out.println("\n\nCantidad de nodos: "+tree.cont);
        System.out.println( "Altura del árbol: " + tree.root.getHeight());
        System.out.println("\n" +tree.find(100) );
        System.out.println( tree.find(5) );
    } 
} 

