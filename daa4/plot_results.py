import csv
from pathlib import Path
from statistics import median
import matplotlib.pyplot as plt

rows = list(csv.DictReader(open('results/results.csv')))
values = {}
for r in rows:
    key = (r['algorithm'], r['input'], int(r['n']))
    values.setdefault(key, []).append(r)

def plot(filename, title, ylabel, algorithms, kind, field, log=False):
    plt.figure(figsize=(7,4.5))
    for alg in algorithms:
        data = sorted((n, median(float(row[field]) for row in runs)) for (a,t,n),runs in values.items() if a==alg and t==kind)
        plt.plot([x for x,y in data],[y for x,y in data],marker='o',label=alg)
    plt.title(title)
    plt.xlabel('Input size n (characters)')
    plt.ylabel(ylabel)
    if log: plt.yscale('log')
    plt.legend(); plt.grid(alpha=.25); plt.tight_layout()
    plt.savefig('results/plots/'+filename,dpi=150)
    plt.close()

Path('results/plots').mkdir(parents=True,exist_ok=True)
plot('edit_operations.png','Edit distance: operations on different strings','Operations (count)', ['recursive','table','two_rows'],'different','operations',True)
plot('edit_time.png','Edit distance DP: measured time','Median time (ms)', ['table','two_rows'],'random','time_ms')
plot('search_comparisons.png','String search: repeated text','Character comparisons (count)', ['naive','kmp'],'repeated','operations')
print('Saved 3 plots')
