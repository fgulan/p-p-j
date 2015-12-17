docs = """
Simple evaluator for UTR
Original by: http://www.fer2.net/showpost.php?p=2144786&postcount=68
Modified by: srolija

Version 1.1
- added flags for simpler runing of multiple tests
- removes temporrary files for correct test cases
- outputs error log and stops when runtime error encountered
- cleaned up code


How to run it?
--------------------------------------------------------------------------------

Defaults (python3, current directory contains testXX directories):
python test.py "program.exe"

Custom test directory (lab1_primjeri_2014)
python test.py "program.exe" --tests ./lab1_primjeri_2014

Custom file names (input.in & output.out):
python test.py "program.exe" --input test.in --output test.out

For python2 (download and install both)
C:\Python2\bin\python.exe test.py "C:\Python3\bin\python.exe program.py"

--------------------------------------------------------------------------------
"""

import argparse
import os
import subprocess
import sys
import tempfile
import time

def get_tests() :
    tests = [] # Get all directories from current folder
    test_directory = os.path.relpath(".")

    for item in list(filter(os.path.isdir, [os.path.join(test_directory, d) for d in os.listdir(test_directory)])):

        # Check if they contain both input and output file
        if os.path.exists(os.path.join( item, item + ".in")) and \
                os.path.exists(os.path.join( item, item + ".out")):

            # If they do, add them to the list
            tests.append(item)

    return tests



def execute(tests) :

    if not tests :
        print("There are no tests to be run! Check your setup.")
        return

    execution, correct = 0, 0
    template_status = "[ {:6s} ] | {}"

    print("\nTotal of {} tests will be run!".format(len(tests)), end="\n\n")

    ERROR_LOG = tempfile.TemporaryFile()

    for test in tests :

        # Generate paths for current test
        INPUT = os.path.join(test, test + ".in")
        OUTPUT = os.path.join(test, test + ".out")
        TMP = os.path.join(test, test + ".u")

        # Construct command line
        cmd = 'java SemantickiAnalizator + < ' + INPUT + " > " + TMP

        # Run program
        start = time.time()
        status = subprocess.call(cmd, shell=True, stdin=subprocess.PIPE, stdout=subprocess.DEVNULL, stderr=ERROR_LOG)
        end = time.time()

        ok = False

        # Check if output is equal to expected
        with open (OUTPUT, 'U') as input_file:
            with open (TMP, 'U') as output_file:
                ok = input_file.read() == output_file.read()

        # Upon success remove output file
        if ok:
            os.remove(TMP)


        if ok : correct += 1
        print(template_status.format(test, ('pass' if ok else 'fail - x')))

    print('\nStats : {}/{} | {:.0f}%'.format(correct, len(tests), (correct / len(tests) * 100)))

# Let's run this motherfucker!!!
execute(get_tests())
