KR_INT 1 int
IDN 1 jednako
L_ZAGRADA 1 (
KR_CHAR 1 char
IDN 1 a
ZAREZ 1 ,
KR_CHAR 1 char
IDN 1 b
D_ZAGRADA 1 )
L_VIT_ZAGRADA 1 {
KR_IF 2 if
L_ZAGRADA 2 (
IDN 2 a
OP_EQ 2 ==
IDN 2 b
D_ZAGRADA 2 )
L_VIT_ZAGRADA 2 {
KR_RETURN 3 return
BROJ 3 1
TOCKAZAREZ 3 ;
D_VIT_ZAGRADA 4 }
KR_ELSE 4 else
L_VIT_ZAGRADA 4 {
KR_RETURN 5 return
BROJ 5 0
TOCKAZAREZ 5 ;
D_VIT_ZAGRADA 6 }
D_VIT_ZAGRADA 7 }
KR_VOID 9 void
IDN 9 greska
L_ZAGRADA 9 (
KR_INT 9 int
IDN 9 x
D_ZAGRADA 9 )
L_VIT_ZAGRADA 9 {
KR_CHAR 10 char
IDN 10 a
OP_PRIDRUZI 10 =
ZNAK 10 'a'
TOCKAZAREZ 10 ;
KR_RETURN 11 return
IDN 11 a
TOCKAZAREZ 11 ;
D_VIT_ZAGRADA 12 }
KR_INT 14 int
IDN 14 x
OP_PRIDRUZI 14 =
BROJ 14 4
TOCKAZAREZ 14 ;
KR_VOID 15 void
IDN 15 f3
L_ZAGRADA 15 (
KR_VOID 15 void
D_ZAGRADA 15 )
L_VIT_ZAGRADA 15 {
KR_INT 16 int
IDN 16 x
OP_PRIDRUZI 16 =
BROJ 16 3
TOCKAZAREZ 16 ;
KR_RETURN 17 return
IDN 17 x
TOCKAZAREZ 17 ;
D_VIT_ZAGRADA 18 }
KR_INT 20 int
IDN 20 main
L_ZAGRADA 20 (
KR_VOID 20 void
D_ZAGRADA 20 )
L_VIT_ZAGRADA 20 {
KR_INT 21 int
IDN 21 a
TOCKAZAREZ 21 ;
KR_CHAR 22 char
IDN 22 i
OP_PRIDRUZI 22 =
ZNAK 22 'a'
TOCKAZAREZ 22 ;
KR_CHAR 23 char
IDN 23 e
OP_PRIDRUZI 23 =
ZNAK 23 'b'
TOCKAZAREZ 23 ;
IDN 25 a
OP_PRIDRUZI 25 =
IDN 25 jednako
L_ZAGRADA 25 (
IDN 25 i
ZAREZ 25 ,
IDN 25 e
D_ZAGRADA 25 )
TOCKAZAREZ 25 ;
IDN 26 greska
L_ZAGRADA 26 (
D_ZAGRADA 26 )
KR_RETURN 27 return
BROJ 27 1
TOCKAZAREZ 27 ;
D_VIT_ZAGRADA 28 }
