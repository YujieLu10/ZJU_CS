// This assignment may cost you some efferts, so I give you some important HINTS, hope that may help you.
// Enjoy the coding and thinking, do pay more attention to the library functions used in OPENGL, such as how they are used? what are the parameters used? and why?


#include <stdlib.h>
#include <GLUT/GLUT.h>

// glutEx1.cpp : ¶¨Òå¿ØÖÆÌ¨Ó¦ÓÃ³ÌÐòµÄÈë¿Úµã¡£
//
//×¢ÒâFPSº¯ÊýµÄÓ¦ÓÃ

#include <stdlib.h>
#include <GLUT/GLUT.h>
#include <stdio.h>
#include <string.h>

#include "stanford_bunny.h"

float eye[] = {0, 4, 6};
float center[] = {0, 0, 0};
float fDistance = 0.2f;
float fRotate = 0;
int cnt = 1;
bool bAnim = false;

bool bDrawList = false;
GLint tableList=0;
GLint rabbitList = 0;

void DrawTable()
{
    glPushMatrix();
    glTranslatef(0, 3.5, 0);
    glScalef(10, 1, 10);
    glutSolidCube(1.0);
    glPopMatrix();
    
    glPushMatrix();
    glTranslatef(1.5, 0.5, 1);
    glScalef(1, 5, 2);
    glutSolidCube(1.0);
    glPopMatrix();
    
    glPushMatrix();
    glTranslatef(-1.5, 0.5, 1);
    glScalef(1, 5, 2);
    glutSolidCube(1.0);
    glPopMatrix();
    
    glPushMatrix();
    glTranslatef(1.5, 0.5, -1);
    glScalef(1, 5, 2);
    glutSolidCube(1.0);
    glPopMatrix();
    
    glPushMatrix();
    glTranslatef(-1.5, 0.5, -1);
    glScalef(1, 5, 2);
    glutSolidCube(1.0);
    glPopMatrix();
}

GLint GenTableList()
{
    GLint lid=glGenLists(2);
    glNewList(lid, GL_COMPILE);

    
    DrawTable();
    glEndList();
    

    glNewList(lid + 1, GL_COMPILE);
    DrawBunny();
    glEndList();
    
    return lid;
}


void Draw_Table_Bunny_List()  //TODO:new way
{
    glPushMatrix();
    //glTranslatef(2.2, 4.5, 1.5);
    glTranslatef(-3.0, 4.5, -4.0);
    glScalef(2, 2, 2);
    for(int i = 1; i < cnt; i++){
       // glCallList(rabbitList);
        if(i % 4 == 1)
            glTranslatef(4.0f, 0.0f, 1.0f);
        glTranslatef(-1.0, 0.0f, 0.0f);
        glCallList(rabbitList);
     //   DrawBunny();
    }
    
    glPopMatrix();
    
    glCallList(tableList);
}

void DrawScene()
{
    glPushMatrix();
    glTranslatef(-3.0, 4.5, -4.0);
    glScalef(2, 2, 2);
    for(int i = 1; i < cnt; i++){
        if(i % 4 == 1)
            glTranslatef(4.0f, 0.0f, 1.0f);
        glTranslatef(-1.0, 0.0f, 0.0f);
        DrawBunny();
    }

    glPopMatrix();
    
    DrawTable();
}

void reshape(int width, int height)
{
    if (height==0)										// Prevent A Divide By Zero By
    {
        height=1;										// Making Height Equal One
    }
    
    glViewport(0,0,width,height);						// Reset The Current Viewport
    
    glMatrixMode(GL_PROJECTION);						// Select The Projection Matrix
    glLoadIdentity();									// Reset The Projection Matrix
    
    float whRatio = (GLfloat)width/(GLfloat)height;
    gluPerspective(45, whRatio, 1, 1000);
    
    glMatrixMode(GL_MODELVIEW);							// Select The Modelview Matrix
}

void idle()
{
    glutPostRedisplay();
}

void key(unsigned char k, int x, int y)
{
    switch(k)
    {
        case 27:
        case 'q': {exit(0); break; }
        
        case 'a':
        {
            eye[0] += fDistance;
            center[0] += fDistance;
            break;
        }
        case 'd':
        {
            eye[0] -= fDistance;
            center[0] -= fDistance;
            break;
        }
        case 'w':
        {
            eye[1] -= fDistance;
            center[1] -= fDistance;
            break;
        }
        case 's':
        {
            eye[1] += fDistance;
            center[1] += fDistance;
            break;
        }
        case 'z':
        {
            eye[2] *= 0.95;
            break;
        }
        case 'c':
        {
            eye[2] *= 1.05;
            break;
        }
        case 'l':
        {
            bDrawList = !bDrawList;	// ÇÐ»»ÏÔÊ¾ÁÐ±íºÍ·ÇÏÔÊ¾ÁÐ±í»æÖÆ·½Ê½
            break;
        }
        case ' ':
        {
            bAnim = !bAnim;
            break;
        }
        case 'i':
        {
            if(cnt<17)
                cnt++;
            break;
        }
        case 'k':
        {
            cnt--;
            break;
        }
        default: break;
    }
}

void getFPS()
{
    static int frame = 0, time, timebase = 0;
    static char buffer[256];
    
    char mode[64];
    if (bDrawList)
    strcpy(mode, "display list");
    else
    strcpy(mode, "naive");
    
    frame++;
    time=glutGet(GLUT_ELAPSED_TIME);
    if (time - timebase > 1000) {
        sprintf(buffer,"FPS:%4.2f %s",
                frame*1000.0/(time-timebase), mode);
        timebase = time;
        frame = 0;
    }
    
    char *c;
    glDisable(GL_DEPTH_TEST);
    glMatrixMode(GL_PROJECTION);  // Ñ¡ÔñÍ¶Ó°¾ØÕó
    glPushMatrix();               // ±£´æÔ­¾ØÕó
    glLoadIdentity();             // ×°Èëµ¥Î»¾ØÕó
    glOrtho(0,480,0,480,-1,1);    // Î»ÖÃÕýÍ¶Ó°
    glMatrixMode(GL_MODELVIEW);   // Ñ¡ÔñModelview¾ØÕó
    glPushMatrix();               // ±£´æÔ­¾ØÕó
    glLoadIdentity();             // ×°Èëµ¥Î»¾ØÕó
    glRasterPos2f(10,10);
    for (c=buffer; *c != '\0'; c++) {
        glutBitmapCharacter(GLUT_BITMAP_HELVETICA_18, *c);
    }
    glMatrixMode(GL_PROJECTION);  // Ñ¡ÔñÍ¶Ó°¾ØÕó
    glPopMatrix();                // ÖØÖÃÎªÔ­±£´æ¾ØÕó
    glMatrixMode(GL_MODELVIEW);   // Ñ¡ÔñModelview¾ØÕó
    glPopMatrix();                // ÖØÖÃÎªÔ­±£´æ¾ØÕó
    glEnable(GL_DEPTH_TEST);
}

void redraw()
{
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glClearColor(0, 0.5, 0, 1);
    glLoadIdentity();									// Reset The Current Modelview Matrix
    
    gluLookAt(eye[0], eye[1], eye[2],
              center[0], center[1], center[2],
              0, 1, 0);				// ³¡¾°£¨0£¬0£¬0£©µÄÊÓµãÖÐÐÄ (0, 5, 50)£¬YÖáÏòÉÏ
    
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_LIGHTING);
    GLfloat gray[] = { 0.4, 0.4, 0.4, 1.0 };
    GLfloat light_pos[] = {10, 10, 10, 1};
    glLightModelfv(GL_LIGHT_MODEL_AMBIENT,gray);
    glLightfv(GL_LIGHT0, GL_POSITION, light_pos);
    glLightfv(GL_LIGHT0, GL_AMBIENT, gray);
    glEnable(GL_LIGHT0);
    
    if (bAnim) 
    fRotate += 0.5f;
    glRotatef(fRotate, 0, 1.0f, 0);			// Rotate around Y axis
    
    glScalef(0.4, 0.4, 0.4);
    if(!bDrawList)
    DrawScene();						// old way
    else 
    Draw_Table_Bunny_List();                  // new way
    
    getFPS();
    glutSwapBuffers();
}

int main (int argc,  char *argv[])
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA | GLUT_DEPTH | GLUT_DOUBLE);
    glutInitWindowSize(480,480);
    int windowHandle = glutCreateWindow("Exercise 4");
    
    glutDisplayFunc(redraw);
    glutReshapeFunc(reshape);	
    glutKeyboardFunc(key);
    glutIdleFunc(idle);
    
    tableList = GenTableList();
    rabbitList = tableList + 1;
    
    glutMainLoop();
    return 0;
}


