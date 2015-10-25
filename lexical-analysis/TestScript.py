import os
import shutil
import subprocess
import sys
import tempfile
import time

def get_tests():
    tests = []
    test_directory = os.path.relpath("examples")
	
    for item in list(filter(os.path.isdir, [os.path.join(test_directory, d)	for d in os.listdir(test_directory)])):
        tests.append(item)

    return tests
	
def copytree(src, dst):
    for item in os.listdir(src):
        s = os.path.join(src, item)
        d = os.path.join(dst, item)
        if os.path.isdir(s):
            shutil.copytree(s, d)
        else:
            shutil.copy2(s, d)
			
def prepare_for_compile():
    copytree("src/main/java", "system-test")
    copytree("src/main/java/hr", "system-test/analizator/hr")

def compile():
    subprocess.call("javac GLA.java", shell=True)
    os.chdir("analizator")
    subprocess.call("javac LA.java", shell=True)
    os.chdir("..")

def run_generator(generator_input):
    generator_command = "java GLA"+ " < " + generator_input
    generator_start = time.time()
    generator_status = subprocess.call(generator_command, shell=True)
    generator_end = time.time()

    return generator_end - generator_start

def run_analyzer(analyzer_input, analyzer_temp, analyzer_err, analyzer_output):
    analyzer_command = "java LA" + " < " + analyzer_input + " > " + "analyzer_temp" + " 2> " + "analyzer_err"

    analyzer_start = time.time()
    subprocess.call(analyzer_command, shell=True, stdin=subprocess.PIPE, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
    analyzer_end = time.time()

    os.chdir("..")
    ok = False
    with open(analyzer_output, 'r') as input_file:
        with open(analyzer_temp, 'r') as output_file:
            ok = input_file.read() == output_file.read()

    if ok:
         os.remove(analyzer_temp)
         os.remove(analyzer_err)

    return analyzer_end - analyzer_start, ok

def run_tests():
    tests = get_tests()

    print("\nTotal of {} tests will be run!".format(len(tests)), end="\n\n")

    correct = 0
    for test in tests:
        generator_time = run_generator(os.path.join(test, "test.lan"))
        analyzer_input = os.path.join(test, "test.in")
        open(os.path.join(test, "test.u"), 'w').close()
        analyzer_temp = os.path.join(test, "test.u")
        open(os.path.join(test, "test.err"), 'w').close()
        analyzer_err = os.path.join(test, "test.err")
        analyzer_output = os.path.join(test, "test.out")
        os.chdir("analizator")
        analyzer_time, ok = run_analyzer(analyzer_input, analyzer_temp, analyzer_err, analyzer_output)
        if ok:
            correct += 1
        print("%-40s %.3f ms %.3f ms %s" % (test, generator_time * 1000, analyzer_time * 1000, "pass" if ok else "fail"))

def remove_readonly(func, path, excinfo):
    os.chmod(path, stat.S_IWRITE)
    func(path)

def delete_copied_files():
    for item in os.listdir(os.getcwd()):
        if item != "examples":
            if os.path.isdir(item):
                shutil.rmtree(item, onerror=remove_readonly)
            else:
                os.remove(item)

prepare_for_compile()
os.chdir("system-test")
compile()
run_tests()
delete_copied_files()