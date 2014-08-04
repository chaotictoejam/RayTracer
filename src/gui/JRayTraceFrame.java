package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*; 
import ray_tracing.*;
import geometries.*;
import java.io.*;

import java.awt.event.*;

class TMouseListener extends MouseAdapter{
	public void mouseClicked(MouseEvent event){
		System.out.printf("(%d,%d) clicked\n",
				event.getX(),event.getY());
	}
}
class RendererPanel extends JPanel{
	
	private RayTracer rayTracer;
	
	public RendererPanel(RayTracer rayTracer){
		super();
		this.rayTracer=rayTracer;
		addMouseListener(new TMouseListener());
		setBackground(java.awt.Color.gray);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d=(Graphics2D)g;
		BufferedImage texture=rayTracer.getScreen();
		g2d.setPaint(new TexturePaint(texture,
				new Rectangle(texture.getWidth(),texture.getHeight())));
		g2d.fill(new Rectangle2D.Double(20,20,
				texture.getWidth(),texture.getHeight()));
	}
}

public class JRayTraceFrame extends JFrame {
	public final static int WIDTH=600;
	public final static int HEIGHT=600;
	//public final static String path="F:\\Joanne\\workspace\\Assignment5\\src\\test.jpg";
	private RayTracer rayTracer=null;
	private RendererPanel renderer;
	public static String path = "http://jlsirois.com/test.jpg";
	private Sphere reflectiveSphere=null;
	private Sphere refractiveSphere=null;
	
	public void initRayTracer(String path){
		
		// setting up the view for the ray tracer
		Vector3 eye=new Vector3(0,4,4);
		Vector3 lookat=new Vector3(0,-1,-1.75);
		Vector3 up=new Vector3(0,1,0);
		rayTracer=new RayTracer(WIDTH,HEIGHT,eye,lookat,up);
		
		// setting up the objects
		Vector3 p0=new Vector3(-1.5,-1,-0.5);
		Vector3 p1=new Vector3(-1.5,-1,-3.0);
		Vector3 p2=new Vector3(1.5,-1,-3.0);
		Vector3 p3=new Vector3(1.5,-1,-0.5);

		TexturedRectangle3D rect=
			new TexturedRectangle3D(p0,p1,p2,p3,path);
		rect.setSurfaceType(Geometry.DIFFUSE);
		rect.setDiffuse(0.9);
		
		rayTracer.addGeometry(rect);

 		// reflective ball
		reflectiveSphere=new Sphere(new Vector3(0,-0.5,-2.0),0.5);
		reflectiveSphere.setSurfaceType(Geometry.SPECULAR);
		
		//refractive ball
		refractiveSphere=new Sphere(new Vector3(0,-0.5,-2.0),0.5);
		refractiveSphere.setSurfaceType(Geometry.TRANSPARENT);
		refractiveSphere.setRefraction(1.1);
		refractiveSphere.setRefractionRadio(0.9);
		refractiveSphere.setColor(new Color(0,0,255));
		
		rayTracer.addGeometry(reflectiveSphere);
		
		rayTracer.setLightSource(new Vector3(0,4,4));
		
		rayTracer.update();
	}
	
	public void initComponent(){
		renderer=new RendererPanel(rayTracer);
		add(renderer,BorderLayout.CENTER);
		
		JPanel control=new JPanel();
		control.setLayout(new FlowLayout());
		ButtonGroup radioGroup=new ButtonGroup();
		JRadioButton reflection=new JRadioButton("Reflection", true);
		JRadioButton refraction=new JRadioButton("Refraction",false);
		radioGroup.add(reflection);
		radioGroup.add(refraction);
		
		control.add(reflection);
		control.add(refraction);
		
		reflection.addItemListener(new RadioButtonHandler(reflectiveSphere));
		refraction.addItemListener(new RadioButtonHandler(refractiveSphere));
		
		add(control,BorderLayout.SOUTH);
	}
	
	public JRayTraceFrame(String title){
		super(title);
		initRayTracer(path);
		initComponent();
	}
	
	private class RadioButtonHandler implements ItemListener{
		private Geometry sphere;
		
		public RadioButtonHandler(Geometry sphere){
			this.sphere=sphere;
		}
		
		public void itemStateChanged(ItemEvent event){

			if(!((JRadioButton)(event.getSource())).isSelected()){
				rayTracer.removeGeometry(sphere);
			}else{
				//System.out.println("hello");
				rayTracer.addGeometry(sphere);
				rayTracer.update();
				renderer.repaint();
			}
		}
	}
	
	public static void main(String args[]){
		//path = args[0];

		JRayTraceFrame frame=new JRayTraceFrame("Ray Tracing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH+50, HEIGHT+70);
		frame.setVisible(true);
	}
}
