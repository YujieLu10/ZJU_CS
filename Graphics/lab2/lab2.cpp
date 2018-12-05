// main.cpp : ¶¨Òå¿ØÖÆÌ¨Ó¦ÓÃ³ÌÐòµÄÈë¿Úµã¡£
//

#include "GLUT/GLUT.h"
#include <stdio.h>
#include <stdlib.h>
#define BMP_Header_Length 54
float fTranslate;
float fRotate;
float fScale;
GLuint textdesk, textlen;
void Draw_Triangle()
{
    glBegin(GL_TRIANGLES);
    glColor3f(1.0f, 0.0f, 0.0f);
    glVertex3f(0.0f, 1.0f, 0.0f);
    glColor3f(0.0f, 1.0f, 0.0f);
    glVertex3f(-1.0f,-1.0f, 0.0f);
    glColor3f(0.0f, 0.0f,1.0f);
    glVertex3f( 1.0f,-1.0f, 0.0f);
    glEnd();
}

void DrawCube(GLfloat x1,GLfloat x2, GLfloat y1, GLfloat y2, GLfloat z1, GLfloat z2){
    int i, j;
    GLfloat vertex[8][3] = {
        x1, y1, z1, // [6 5]
        x1, y2, z1, // [7 4]
        x2, y2, z1, // [1 2]
        x2, y1, z1, // [0 3]
        x2, y1, z2,
        x2, y2, z2,
        x1, y2, z2,
        x1, y1, z2,
    };
    GLint surface[6][4] = {
        0, 1, 2, 3,
        4, 5, 6, 7,
        2, 3, 4, 5,
        1, 2, 5, 6,
        0, 1, 6, 7,
        0, 3, 7, 4,
    };
    glBegin(GL_QUADS);
    for(i = 0; i < 6; i++){
        for(j = 0; j < 4; j++){
            glEnable(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, textdesk);
            glVertex3fv(vertex[surface[i][j]]);
        }
    }
    glEnd();
}

void DrawDesk()
{
    DrawCube(0.0, 1.0, 0.0, 0.8, 0.6, 0.8);
    
}
void DrawLen(){
    DrawCube(0.1, 0.3, 0.1, 0.3, 0.0, 0.6);
    DrawCube(0.7, 0.9, 0.1, 0.3, 0.0, 0.6);
    DrawCube(0.1, 0.3, 0.5, 0.7, 0.0, 0.6);
    DrawCube(0.7, 0.9, 0.5, 0.7, 0.0, 0.6);
}

void reshape(int width, int height)
{
    if (height==0)
    {
        height=1;
    }
    
    glViewport(0,0,width,height);
    
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    
    // Calculate The Aspect Ratio Of The Window
    gluPerspective(45.0f,(GLfloat)width/(GLfloat)height,0.1f,100.0f);
    
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

void idle()
{
    glutPostRedisplay();
}

void redraw()
{
    // If want display in wireframe mode
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glEnable(GL_DEPTH_TEST);

    
    glLoadIdentity();
    //glDepthFunc(GL_LEQUAL);  // 设置深度缓存
    //glMatrixMode(GL_PROJECTION);
    
    glEnable(GL_TEXTURE_2D );
   // glBindTexture(GL_TEXTURE_2D, textdesk);
    glPushMatrix();
    glTranslatef(-2.5f, 0.0f,-6.0f);
    glTranslatef(0.0f, fTranslate, 0.0f);
    //Draw_Triangle();
    DrawDesk();
  //  glBindTexture(GL_TEXTURE_2D, textlen);
    DrawLen();
    glPopMatrix();
    
    //center Desk matrix
    
    glPushMatrix();
    glTranslatef(0.0f, 0.0f,-6.0f);
    glRotatef(fRotate, 0, 1.0f, 0);
    //glBindTexture(GL_TEXTURE_2D, textdesk);
    DrawDesk();						// Draw triangle
    //glBindTexture(GL_TEXTURE_2D, textdesk);
    DrawLen();
    glPopMatrix();
    
    //right Desk
    
    glPushMatrix();
    glTranslatef(1.5f, 0.0f,-6.0f);
    glScalef(fScale, fScale, fScale);
    glBindTexture(GL_TEXTURE_2D, textlen);
    DrawDesk();
    glBindTexture(GL_TEXTURE_2D, textlen);
    DrawLen();
    glPopMatrix();
    
    fTranslate += 0.005f;
    fRotate    += 0.5f;
    fScale     -= 0.005f;
    
    if(fTranslate > 0.5f) fTranslate = 0.0f;
    if(fScale < 0.5f) fScale = 1.0f;
    glutSwapBuffers();
}
//power of two
int power_of_two(int n)
{
    if( n <= 0 )
        return 0;
    return (n & (n-1)) == 0;
}
//load texture function
GLuint load_texture(const char* file_name)
{
    GLint width, height, total_bytes;
    GLubyte* pixels = 0;
    GLint last_texture_ID=0;
    GLuint texture_ID = 0;
    
    
    FILE* pFile = fopen(file_name, "rb");
    if( pFile == 0 ){
        printf("pfile = 0\n");
        return 0;
    }
    
    fseek(pFile, 0x0012, SEEK_SET);
    fread(&width, 4, 1, pFile);
    fread(&height, 4, 1, pFile);
    fseek(pFile, BMP_Header_Length, SEEK_SET);
    
    {
        GLint line_bytes = width * 3;
        while( line_bytes % 4 != 0 )
            ++line_bytes;
        total_bytes = line_bytes * height;
    } //{
    
    pixels = (GLubyte*)malloc(total_bytes);
    if( pixels == 0 ){
        fclose(pFile);
        return 0;
    } //if
    
    if( fread(pixels, total_bytes, 1, pFile) <= 0 ){
        free(pixels);
        fclose(pFile);
        return 0;
    } //if
    
    {
        GLint max;
        glGetIntegerv(GL_MAX_TEXTURE_SIZE, &max);
        if( !power_of_two(width)|| !power_of_two(height)|| width > max|| height > max ){
            const GLint new_width = 256;
            const GLint new_height = 256;
            GLint new_line_bytes, new_total_bytes;
            GLubyte* new_pixels = 0;
            
            new_line_bytes = new_width * 3;
            while( new_line_bytes % 4 != 0 )
                ++new_line_bytes;
            new_total_bytes = new_line_bytes * new_height;
            
            new_pixels = (GLubyte*)malloc(new_total_bytes);
            if( new_pixels == 0 ){
                free(pixels);
                fclose(pFile);
                return 0;
            }//if
            
            gluScaleImage(GL_RGB,width, height, GL_UNSIGNED_BYTE, pixels,new_width, new_height, GL_UNSIGNED_BYTE, new_pixels);
            free(pixels);
            pixels = new_pixels;
            width = new_width;
            height = new_height;
        }//if
    }//{
    
    glGenTextures(1, &texture_ID);
    if( texture_ID == 0 ) {
        free(pixels);
        fclose(pFile);
        return 0;
    } //if
    
    glGetIntegerv(GL_TEXTURE_BINDING_2D, &last_texture_ID);
    glBindTexture(GL_TEXTURE_2D, texture_ID);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_BGR_EXT, GL_UNSIGNED_BYTE, pixels);
    glBindTexture(GL_TEXTURE_2D, last_texture_ID);
    
    
    free(pixels);
    return texture_ID;
}

int main (int argc,  char *argv[])
{
    
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE);
    glutInitWindowSize(640,480);
    glutCreateWindow("Exercise2");
    
    glClearColor(0,0,0,0);
    
    glEnable(GL_TEXTURE_2D);
    
    glTexEnvf(GL_TEXTURE_ENV,GL_TEXTURE_ENV_MODE,GL_REPLACE);
    
    glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER,GL_TRUE);
    

    
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_COLOR_MATERIAL);
    
    glShadeModel(GL_SMOOTH);
    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

    glEnable(GL_DEPTH_TEST);

    textdesk=load_texture("/Users/c-ten/Desktop/roamtest/GLUTTest/tdesk.bmp");
    //textdesk = load_texture("/Users/c-ten/Desktop/roamtest/GLUTTest/bottom.bmp");
    textlen = load_texture("/Users/c-ten/Desktop/roamtest/GLUTTest/backwall.bmp");
    glutDisplayFunc(redraw);
    glutReshapeFunc(reshape);		
    glutIdleFunc(redraw);
    
    glutMainLoop();
    
    return 0;
}


