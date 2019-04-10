#include<GLUT/GLUT.h>
#include<iostream>
#include<cmath>
#include<stdio.h>

const GLfloat PI = 3.1415926f;
GLfloat vertexX[12], vertexY[12];
GLfloat CenterX[5] = { -0.5,-0.25 ,-0.15 ,-0.15 ,-0.25 };
GLfloat CenterY[5] = { 0.25,0.4,0.3,0.15,0.05 };
GLfloat OutX[5] = { -0.5 };
GLfloat OutY[5] = {0.4};

void myGLStar(GLfloat CenterX, GLfloat CenterY, GLfloat OutX, GLfloat OutY,int flag)
{
    glBegin(GL_TRIANGLE_FAN);
    vertexX[0] = CenterX;    //center
    vertexY[0] = CenterY;
    vertexX[1] = OutX;    //out vertex
    vertexY[1] = OutY;
    GLfloat lenOut = sqrt((CenterX - OutX)*(CenterX - OutX) + (CenterY - OutY)*(CenterY - OutY));  //long
    GLfloat lenIn = lenOut*sin(0.1*PI) / sin(0.7* PI);
    double angle = atan((vertexY[1] - vertexY[0]) / (vertexX[1] - vertexX[0]));  //vertex and center angle with X
    if (flag)
        angle = angle - PI;
    for (int i = 2; i < 12; i++) {
        angle = angle - 0.2*PI;
        if (i % 2 == 0) {
            vertexX[i] = lenIn*cos(angle) + vertexX[0];
            vertexY[i] = lenIn*sin(angle) + vertexY[0];
        }
        else {
            vertexX[i] = lenOut*cos(angle) + vertexX[0];
            vertexY[i] = lenOut*sin(angle) + vertexY[0];
        }
    }
    for (int i = 0; i < 12; i++)
        glVertex2f(vertexX[i], vertexY[i]);
    glEnd();
}
void display()
{
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f(1, 0, 0);
    glBegin(GL_QUADS);
    glVertex2f(-0.75, 0.5);
    glVertex2f(0.75, 0.5);
    glVertex2f(0.75, -0.5);
    glVertex2f(-0.75, -0.5);
    glEnd();
    
    glColor3f(1.0, 1.0, 0.0);
    
    for (int i = 1; i < 5; i++) {
        OutX[i] = CenterX[i]-0.05*cos(atan((CenterY[0] - CenterY[i]) / (CenterX[0] - CenterX[i])));
        OutY[i] = CenterY[i]-0.05*sin(atan((CenterY[0] - CenterY[i]) / (CenterX[0] - CenterX[i])));
    }
    myGLStar(CenterX[0], CenterY[0], OutX[0], OutY[0], 0);
    myGLStar(CenterX[1], CenterY[1], OutX[1], OutY[1], 1);
    myGLStar(CenterX[2], CenterY[2], OutX[2], OutY[2], 1);
    myGLStar(CenterX[3], CenterY[3], OutX[3], OutY[3], 1);
    myGLStar(CenterX[4], CenterY[4], OutX[4], OutY[4], 1);
    glutSwapBuffers();
}

int main (int argc,  char *argv[])
{
    glutInit(&argc,argv);
    glutInitDisplayMode(GLUT_RGB|GLUT_DOUBLE);
    glutInitWindowPosition(10, 10);
    glutInitWindowSize(600,600);
    glutCreateWindow("Graphics-Lab1");
    glClear(GL_COLOR_BUFFER_BIT);
    glutDisplayFunc(display);
    glutMainLoop();
    
    return 0;
}



