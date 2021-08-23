import pandas as pd
import numpy as np

# Reading relevant files for US 1.3
accident = pd.read_csv('ACCIDENT.csv')
vehicle = pd.read_csv('VEHICLE.csv')
node = pd.read_csv('NODE.csv')

# Merging the tables 
result = pd.merge(accident, vehicle, on='ACCIDENT_NO')
result = pd.merge(result, node, on='ACCIDENT_NO')

# Combining the lat-long into a single location tuple
result['location'] = list(zip(result.Lat, result.Long))

# Selecting appropriate columns for US 1.3
result_1 = result[['Vehicle Type Desc', 'LGA_NAME','location']]

# Filtering for only bicycle accidents
result_1 = result_1[result_1['Vehicle Type Desc'] == 'Bicycle']

# Grouping the results by LGA  
results = result_1.groupby(by = 'LGA_NAME')['location'].apply(list).reset_index()

# Removing the first empty location
results = results.iloc[1:, :]

def get_points(name):
    if name in set(results['LGA_NAME']):
        return list(results[results['LGA_NAME'] == name]['location'])
    else:
        return 'error'

#print(get_points('MELBOURNE')[0])

#print(results.iloc[1,:])




