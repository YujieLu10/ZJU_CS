//#include <GLUT/GLUT.h>
//
////void display(void)
////{
////    glClear( GL_COLOR_BUFFER_BIT);
////    glColor3f(0.0, 0.0, 0.0);
////    glBegin(GL_POLYGON);
////    glVertex3f(2.0, 4.0, 0.0);
////    glVertex3f(8.0, 4.0, 0.0);
////    glVertex3f(8.0, 6.0, 0.0);
////    glVertex3f(2.0, 6.0, 0.0);
////    glEnd();
////    glFlush();
////} 
//
////void line(void){
////    glClear(GL_COLOR_BUFFER_BIT);
////    GLfloat list[6][2];
////    glColor3f(0.0, 1.0, 0.0);
////    glBegin(GL_TRIANGLES);
////    for(int i = 0; i < 6; i++){
////        glVertex2fv(list[i]);
////    }
////    glEnd();
////    glFlush();
////}
//int size = 5;
//void drawOneCubeface(void)
//{
//    static GLfloat v[8][3];
//    v[0][0] = v[3][0] = v[4][0] = v[7][0] = -size/2.0;
//    v[1][0] = v[2][0] = v[5][0] = v[6][0] = size/2.0;
//    v[0][1] = v[1][1] = v[4][1] = v[5][1] = -size/2.0;
//    v[2][1] = v[3][1] = v[6][1] = v[7][1] = size/2.0;
//    v[0][2] = v[1][2] = v[2][2] = v[3][2] = -size/2.0;
//    v[4][2] = v[5][2] = v[6][2] = v[7][2] = size/2.0;
//    glBegin( GL_POLYGON);
//    glVertex3fv(v[0]);
//    glVertex2fv(v[1]);
//    glVertex2fv(v[2]);
//    glVertex2fv(v[3]);
//    glEnd();
//    glFlush();
//}
//
//int main(int argc, char **argv)
//{
//    glutInit(&argc, argv);
//    glutInitDisplayMode ( GLUT_SINGLE | GLUT_RGB
//                         | GLUT_DEPTH);
//    
//    glutInitWindowPosition(100,100);
//    glutInitWindowSize(300,300);
//    glutCreateWindow ("square");
//    
//    glClearColor(1.0, 1.0, 1.0, 1.0);
//    glMatrixMode(GL_PROJECTION);
////    glLoadIdentity();
////    glOrtho(0.0, 10.0, 0.0, 10.0, -1.0, 1.0);
//    
//    glutDisplayFunc(drawOneCubeface);
//    glutMainLoop(); 
//    return 0; 
//}



#include <GLUT/GLUT.h>
#include <stdlib.h>
#include <iostream>
static GLfloat spin = 0.0;

void init( void )
{
    glClearColor( 0.0, 0.0, 0.0, 0.0 );
    glShadeModel( GL_FLAT );
}

void display( void )
{
    glClear( GL_COLOR_BUFFER_BIT );
    glPushMatrix();
    glRotatef( spin, 0.0, 0.0, 1.0 );
    glColor3f( 1.0, 1.0, 1.0 );
    glRectf( -25.0, -25.0, 25.0, 25.0 );
    glPopMatrix();
    glutSwapBuffers();
}

void spinDisplay( void )
{
    spin += 2.0;
    if( spin > 360.0 )
        spin -= 360.0;
    glutPostRedisplay();
}

void reshape( int w, int h )
{
    glViewport( 0, 0, (GLsizei) w, (GLsizei) h );
    glMatrixMode( GL_PROJECTION );
    glLoadIdentity();
    glOrtho( -50.0, 50.0, -50.0, 50.0, -1.0, 1.0 );
    glMatrixMode( GL_MODELVIEW );
    glLoadIdentity();
}


void mouse( int button, int state, int x, int y )
{
    switch( button )
    {
        case GLUT_LEFT_BUTTON:
            if( state == GLUT_DOWN )
                glutIdleFunc( spinDisplay );
            break;
        case GLUT_RIGHT_BUTTON:
            if( state == GLUT_DOWN )
                glutIdleFunc( NULL );
            break;
        default:	break;
    }
}

void keyboard(unsigned char key, int x, int y)
{
    switch(key)
    { case 'a': std::cout<<"a Pressed"<<std::endl; break; }
}


int main( int argc, char ** argv )
{
    glutInit( &argc, argv );
    glutInitDisplayMode( GLUT_DOUBLE | GLUT_RGB );
    glutInitWindowSize( 250, 250 );
    glutInitWindowPosition( 100, 100 );
    glutCreateWindow( argv[ 0 ] );
    
    init();
    glutDisplayFunc( display );
    glutReshapeFunc( reshape );
    glutMouseFunc( mouse );
    glutKeyboardFunc( keyboard );
    glutMainLoop();
    return 0;
}








/*
 webresources
 
 WWW.OpenGL.org
 nehe.gamedev.net
 http://www.xmission.com/~nate/glut.html
 
 */


















