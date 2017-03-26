
PROGRAM Test;
VAR
   radius: REAL;

FUNCTION CircleArea(r : REAL): REAL;
BEGIN
    CircleArea := 3.1415 * r * r;
END;

BEGIN
    radius := 5.0;
    radius := CircleArea(radius);
END.
